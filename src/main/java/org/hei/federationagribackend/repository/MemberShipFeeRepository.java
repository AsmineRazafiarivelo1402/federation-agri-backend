package org.hei.federationagribackend.repository;

import org.hei.federationagribackend.dto.StatusActivity;
import org.hei.federationagribackend.entity.Frequency;
import org.hei.federationagribackend.entity.MemberShipFeeEntity;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class MemberShipFeeRepository {

    private final Connection connection;

    public MemberShipFeeRepository(Connection connection) {
        this.connection = connection;
    }

    public List<MemberShipFeeEntity> saveAll(String collectivityId, List<MemberShipFeeEntity> fees) {
        String sql = """
          INSERT INTO membership_fee (id, label, amount, frequency, status, eligible_from, collectivity_id)
                      VALUES (?, ?, ?, ?::frequency_type, ?::activity_status_type, ?, ?)
        """;

        List<MemberShipFeeEntity> saved = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (MemberShipFeeEntity fee : fees) {
                String id = UUID.randomUUID().toString();
                fee.setId(id);
                fee.setCollectivityId(collectivityId);

                ps.setString(1, id);
                ps.setString(2, fee.getLabel());
                ps.setDouble(3, fee.getAmount());
                ps.setString(4, fee.getFrequency().name());
                ps.setString(5, fee.getStatus().name());
                ps.setDate(6, Date.valueOf(fee.getEligibleFrom()));
                ps.setString(7, collectivityId);

                ps.addBatch();
                saved.add(fee);
            }
            ps.executeBatch();
            return saved;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<MemberShipFeeEntity> findByCollectivityId(String collectivityId) {
        String sql = "SELECT id, label, amount, frequency, status, eligible_from, collectivity_id FROM membership_fee WHERE collectivity_id = ?";

        List<MemberShipFeeEntity> list = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, collectivityId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                MemberShipFeeEntity fee = new MemberShipFeeEntity();
                fee.setId(rs.getString("id"));
                fee.setLabel(rs.getString("label"));
                fee.setAmount(rs.getDouble("amount"));
                fee.setFrequency(Frequency.valueOf(rs.getString("frequency")));
                fee.setStatus(StatusActivity.valueOf(rs.getString("status")));
                ps.setString(5, fee.getStatus().name());
                fee.setEligibleFrom(rs.getDate("eligible_from").toLocalDate());
                fee.setCollectivityId(rs.getString("collectivity_id"));

                list.add(fee);
            }

            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<MemberShipFeeEntity> findActiveFeesByCollectivityId(String collectivityId) throws Exception {
        String sql = "SELECT id, label, amount, frequency, status, eligible_from, collectivity_id FROM membership_fee WHERE collectivity_id = ? AND status = 'ACTIVE'";

        List<MemberShipFeeEntity> list = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, collectivityId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                MemberShipFeeEntity fee = new MemberShipFeeEntity();
                fee.setId(rs.getString("id"));
                fee.setLabel(rs.getString("label"));
                fee.setAmount(rs.getDouble("amount"));
                fee.setFrequency(Frequency.valueOf(rs.getString("frequency")));
                fee.setStatus(StatusActivity.valueOf(rs.getString("status")));
                fee.setEligibleFrom(rs.getDate("eligible_from").toLocalDate());
                fee.setCollectivityId(rs.getString("collectivity_id"));

                list.add(fee);
            }
        }

        return list;
    }

    public double getTotalTheoreticalAmountForPeriod(
            String collectivityId,
            LocalDate from,
            LocalDate to
    ) throws Exception {
        String sql = """
        SELECT COALESCE(SUM(
            CASE 
                WHEN frequency = 'ANNUALLY' THEN 
                    amount
                WHEN frequency = 'MONTHLY' THEN 
                    amount * (
                        (EXTRACT(YEAR FROM ?::date) * 12 + EXTRACT(MONTH FROM ?::date))
                        - (EXTRACT(YEAR FROM GREATEST(eligible_from, ?::date)) * 12 + EXTRACT(MONTH FROM GREATEST(eligible_from, ?::date)))
                        + 1
                    )
                WHEN frequency = 'WEEKLY' THEN 
                    amount * (
                        FLOOR((EXTRACT(DOY FROM ?::date) - EXTRACT(DOY FROM GREATEST(eligible_from, ?::date))) / 7) + 1
                    )
                WHEN frequency = 'PUNCTUALLY' THEN 
                    amount
                ELSE 0
            END
        ), 0) as total_theoretical
        FROM membership_fee
        WHERE collectivity_id = ? AND status = 'ACTIVE' AND eligible_from <= ?::date
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setDate(1, java.sql.Date.valueOf(to));
            ps.setDate(2, java.sql.Date.valueOf(to));
            ps.setDate(3, java.sql.Date.valueOf(from));
            ps.setDate(4, java.sql.Date.valueOf(from));


            ps.setDate(5, java.sql.Date.valueOf(to));
            ps.setDate(6, java.sql.Date.valueOf(from));


            ps.setString(7, collectivityId);
            ps.setDate(8, java.sql.Date.valueOf(to));

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total_theoretical");
            }
            return 0.0;
        }
    }
    public boolean collectivityExists(String collectivityId) throws Exception {
        String sql = "SELECT 1 FROM collectivity WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, collectivityId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }
}