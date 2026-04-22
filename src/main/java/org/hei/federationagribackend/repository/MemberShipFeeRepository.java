package org.hei.federationagribackend.repository;

import org.hei.federationagribackend.entity.MemberShipFeeEntity;
import org.springframework.stereotype.Repository;

import java.sql.*;
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
                ps.setString(4, fee.getFrequency());
                ps.setString(5, fee.getStatus());
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
        String sql = "SELECT * FROM membership_fee WHERE collectivity_id = ?";

        List<MemberShipFeeEntity> list = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, collectivityId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                MemberShipFeeEntity fee = new MemberShipFeeEntity();
                fee.setId(rs.getString("id"));
                fee.setLabel(rs.getString("label"));
                fee.setAmount(rs.getDouble("amount"));
                fee.setFrequency(rs.getString("frequency"));
                fee.setStatus(rs.getString("status"));
                fee.setEligibleFrom(rs.getDate("eligible_from").toLocalDate());
                fee.setCollectivityId(rs.getString("collectivity_id"));

                list.add(fee);
            }

            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}