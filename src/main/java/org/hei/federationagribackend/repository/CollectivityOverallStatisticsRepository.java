package org.hei.federationagribackend.repository;

import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CollectivityOverallStatisticsRepository {

    private final Connection connection;

    public CollectivityOverallStatisticsRepository(Connection connection) {
        this.connection = connection;
    }

    public List<Map<String, Object>> getAllCollectivities() throws Exception {
        String sql = "SELECT id, name, registration_number FROM collectivity ORDER BY name";
        List<Map<String, Object>> collectivities = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> collectivity = new HashMap<>();
                collectivity.put("id", rs.getString("id"));
                collectivity.put("name", rs.getString("name") != null ? rs.getString("name") : "Sans nom");
                collectivity.put("number", rs.getString("registration_number"));
                collectivities.add(collectivity);
            }
        }
        return collectivities;
    }

    public Map<String, Integer> countNewMembersByPeriod(LocalDate from, LocalDate to) throws Exception {
        String sql = """
            SELECT collectivity_id, COUNT(*) as new_members_count
            FROM member
            WHERE creation_date BETWEEN ? AND ?
              AND collectivity_id IS NOT NULL
            GROUP BY collectivity_id
        """;
        Map<String, Integer> result = new HashMap<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, java.sql.Date.valueOf(from));
            ps.setDate(2, java.sql.Date.valueOf(to));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.put(rs.getString("collectivity_id"), rs.getInt("new_members_count"));
            }
        }
        return result;
    }

    public Map<String, Integer> countTotalMembersByCollectivity() throws Exception {
        String sql = "SELECT collectivity_id, COUNT(*) as total_members FROM member WHERE collectivity_id IS NOT NULL GROUP BY collectivity_id";
        Map<String, Integer> result = new HashMap<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.put(rs.getString("collectivity_id"), rs.getInt("total_members"));
            }
        }
        return result;
    }

    public Map<String, Double> getTotalActiveFeesByCollectivity() throws Exception {
        String sql = "SELECT collectivity_id, COALESCE(SUM(amount), 0) as total_fees FROM membership_fee WHERE status = 'ACTIVE' GROUP BY collectivity_id";
        Map<String, Double> result = new HashMap<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.put(rs.getString("collectivity_id"), rs.getDouble("total_fees"));
            }
        }
        return result;
    }

    public Map<String, Map<String, Double>> getTotalPaidByMemberByCollectivity(LocalDate from, LocalDate to) throws Exception {
        String sql = """
            SELECT m.collectivity_id, ct.member_debited_id, COALESCE(SUM(ct.amount), 0) as total_paid
            FROM collectivity_transaction ct
            JOIN member m ON ct.member_debited_id = m.id
            WHERE ct.creation_date BETWEEN ? AND ?
            GROUP BY m.collectivity_id, ct.member_debited_id
        """;
        Map<String, Map<String, Double>> result = new HashMap<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, java.sql.Date.valueOf(from));
            ps.setDate(2, java.sql.Date.valueOf(to));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String collId = rs.getString("collectivity_id");
                result.computeIfAbsent(collId, k -> new HashMap<>()).put(rs.getString("member_debited_id"), rs.getDouble("total_paid"));
            }
        }
        return result;
    }


    public Map<String, Double> getOverallAssiduityPercentageByCollectivity(LocalDate from, LocalDate to) throws Exception {
        String sql = """
            SELECT 
                a.collectivity_id,
                COUNT(CASE WHEN aa.attendance_status = 'ATTENDED' THEN 1 END) * 100.0 / NULLIF(COUNT(*), 0) as overall_percentage
            FROM activity a
            JOIN activity_attendance aa ON a.id = aa.activity_id
            WHERE a.executive_date BETWEEN ? AND ?
            GROUP BY a.collectivity_id
        """;
        Map<String, Double> result = new HashMap<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, java.sql.Date.valueOf(from));
            ps.setDate(2, java.sql.Date.valueOf(to));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.put(rs.getString("collectivity_id"), rs.getDouble("overall_percentage"));
            }
        }
        return result;
    }

    public Map<String, Double> getTotalTheoreticalAmountByCollectivityForPeriod(LocalDate from, LocalDate to) throws Exception {
        String sql = """
        SELECT 
            collectivity_id,
            COALESCE(SUM(
                CASE 
                    WHEN frequency = 'ANNUALLY' THEN amount
                    WHEN frequency = 'MONTHLY' THEN 
                        amount * (
                            ((EXTRACT(YEAR FROM ?::date) * 12) + EXTRACT(MONTH FROM ?::date))
                            - ((EXTRACT(YEAR FROM GREATEST(eligible_from, ?::date)) * 12) + EXTRACT(MONTH FROM GREATEST(eligible_from, ?::date)))
                            + 1
                        )
                    WHEN frequency = 'PUNCTUALLY' THEN amount
                    ELSE 0
                END
            ), 0) as total_theoretical
        FROM membership_fee
        WHERE status = 'ACTIVE' AND eligible_from <= ?::date
        GROUP BY collectivity_id
    """;
        Map<String, Double> result = new HashMap<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, java.sql.Date.valueOf(to));
            ps.setDate(2, java.sql.Date.valueOf(to));
            ps.setDate(3, java.sql.Date.valueOf(from));
            ps.setDate(4, java.sql.Date.valueOf(from));
            ps.setDate(5, java.sql.Date.valueOf(to));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.put(rs.getString("collectivity_id"), rs.getDouble("total_theoretical"));
            }
        }
        return result;
    }

    public double calculateUpToDatePercentage(String collectivityId, Double totalFees, Map<String, Double> memberPayments) {
        if (memberPayments == null || memberPayments.isEmpty()) return 0.0;
        double expectedAmount = (totalFees != null) ? totalFees : 0.0;
        long upToDateCount = memberPayments.values().stream().filter(paid -> paid >= expectedAmount).count();
        return (double) upToDateCount / memberPayments.size() * 100.0;
    }


}