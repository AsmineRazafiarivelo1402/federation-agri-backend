package org.hei.federationagribackend.repository;
import org.hei.federationagribackend.entity.AccountType;
import org.hei.federationagribackend.entity.CollectivityTransactionEntity;
import org.hei.federationagribackend.entity.PaymentMode;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CollectivityTransactionRepository {
    private final Connection connection;

    public CollectivityTransactionRepository(Connection connection) {
        this.connection = connection;
    }


    public List<CollectivityTransactionEntity> findByCollectivityAndDates(
            String collectivityId,
            LocalDate from,
            LocalDate to
    ) throws Exception {

        String sql = """
            SELECT ct.id,
                   ct.creation_date,
                   ct.amount,
                   ct.payment_mode,
                   ct.member_debited_id,
                   ct.account_id,
                   fa.account_type
            FROM collectivity_transaction ct
            JOIN financial_account fa ON ct.account_id = fa.id
            WHERE ct.collectivity_id = ?
              AND ct.creation_date BETWEEN ? AND ?
        """;

        List<CollectivityTransactionEntity> result = new ArrayList<>();

        try (
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, collectivityId);
            ps.setDate(2, Date.valueOf(from));
            ps.setDate(3, Date.valueOf(to));

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                CollectivityTransactionEntity e = new CollectivityTransactionEntity();

                e.setId(rs.getString("id"));
                e.setCreationDate(rs.getDate("creation_date").toLocalDate());
                e.setAmount(rs.getDouble("amount"));
                e.setPaymentMode(PaymentMode.valueOf(rs.getString("payment_mode")));

                e.setCollectivityId(collectivityId);
                e.setMemberDebitedId(rs.getString("member_debited_id"));

                e.setAccountId(rs.getString("account_id"));
                e.setAccountType(AccountType.valueOf(rs.getString("account_type")));

                result.add(e);
            }
        }

        return result;
    }
}