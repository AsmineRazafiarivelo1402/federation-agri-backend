package org.hei.federationagribackend.repository;

import org.hei.federationagribackend.entity.MemberPayment;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class MemberPaymentRepository {
    private final Connection connection;

    public MemberPaymentRepository(Connection connection) {
        this.connection = connection;
    }

    public void save(MemberPayment payment) throws SQLException {
        String sql = "INSERT INTO member_payment (id, member_id, amount, membership_fee_id, account_id, payment_mode) VALUES (?, ?, ?, ?, ?, ?::payment_mode_enum)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, payment.getId());
            statement.setString(2, payment.getMemberId());
            statement.setInt(3, payment.getAmount());
            statement.setString(4, payment.getFeeId());
            statement.setString(5, payment.getAccountId());
            statement.setString(6, payment.getPaymentMode());
            statement.executeUpdate();
        }
    }

    public void insertPayment(MemberPayment payment) {
        String sql = "INSERT INTO member_payment (id, member_id, amount, membership_fee_id, account_id, payment_mode, creation_date) VALUES (?, ?, ?, ?, ?, ?::payment_mode_enum, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, payment.getId());
            statement.setString(2, payment.getMemberId());
            statement.setInt(3, payment.getAmount());
            statement.setString(4, payment.getFeeId());
            statement.setString(5, payment.getAccountId());
            statement.setString(6, payment.getPaymentMode());
            statement.setDate(7, java.sql.Date.valueOf(payment.getCreationDate()));

            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Détail de l'erreur SQL : " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
