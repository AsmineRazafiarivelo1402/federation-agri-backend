package org.hei.federationagribackend.repository;
import org.hei.federationagribackend.Interface.FinancialAccount;
import org.hei.federationagribackend.entity.*;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

//    public List<FinancialAccount> findAccountsByCollectivityAndDate(
//            String collectivityId,
//            LocalDate at
//    ) throws Exception {
//
//        String sql = """
//        SELECT fa.id,
//               fa.account_type,
//               COALESCE(SUM(ct.amount), 0) as amount
//        FROM financial_account fa
//        LEFT JOIN collectivity_transaction ct
//          ON fa.id = ct.account_id
//        WHERE fa.collectivity_id = ?
//          AND (ct.creation_date <= ? OR ct.creation_date IS NULL)
//        GROUP BY fa.id, fa.account_type
//    """;
//
//        List<FinancialAccount> result = new ArrayList<>();
//
//        try (PreparedStatement ps = connection.prepareStatement(sql)) {
//
//            ps.setString(1, collectivityId);
//            ps.setDate(2, Date.valueOf(at));
//
//            ResultSet rs = ps.executeQuery();
//
//            while (rs.next()) {
//
//                String id = rs.getString("id");
//                AccountType accountType = AccountType.valueOf(rs.getString("account_type"));
//                double amount = rs.getDouble("amount");
//
//                FinancialAccount account;
//
//
//                if (accountType == AccountType.CASH) {
//
//                    CashAccountEntity cash = new CashAccountEntity();
//                    cash.setId(id);
//                    cash.setAmount(amount);
//                    account = cash;
//
//                } else if (accountType == AccountType.MOBILE_BANKING) {
//
//                    MobileBankingAccountEntity mobile = new MobileBankingAccountEntity();
//                    mobile.setId(id);
//                    mobile.setAmount(amount);
//                    account = mobile;
//
//                } else if (accountType == AccountType.BANK) {
//
//                    BankAccountEntity bank = new BankAccountEntity();
//                    bank.setId(id);
//                    bank.setAmount(amount);
//                    account = bank;
//
//                } else {
//                    throw new RuntimeException("Unknown account type: " + accountType);
//                }
//
//                result.add(account);
//            }
//        }
//
//        return result;
//    }
    public List<FinancialAccount> findAccountsByCollectivityAndDate(
            String collectivityId,
            LocalDate at
    ) throws Exception {

        String sqlAccounts = """
        SELECT id, account_type, collectivity_id
        FROM financial_account
    """;

        String sqlTransactions = """
        SELECT account_id, amount, creation_date
        FROM collectivity_transaction
    """;

        List<FinancialAccount> result = new ArrayList<>();
        Map<String, FinancialAccount> accountMap = new HashMap<>();

        try (PreparedStatement ps = connection.prepareStatement(sqlAccounts)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String id = rs.getString("id");
                String dbCollectivityId = rs.getString("collectivity_id");
                AccountType type = AccountType.valueOf(rs.getString("account_type"));

                if (!dbCollectivityId.equals(collectivityId)) continue;

                FinancialAccount account;

                if (type == AccountType.CASH) {
                    CashAccountEntity cash = new CashAccountEntity();
                    cash.setId(id);

                    account = cash;

                } else if (type == AccountType.MOBILE_BANKING) {
                    MobileBankingAccountEntity mobile = new MobileBankingAccountEntity();
                    mobile.setId(id);

                    account = mobile;

                } else if (type == AccountType.BANK) {
                    BankAccountEntity bank = new BankAccountEntity();
                    bank.setId(id);

                    account = bank;

                } else {
                    throw new RuntimeException("Unknown account type: " + type);
                }

                result.add(account);
                accountMap.put(id, account);
            }
        }

        try (PreparedStatement ps = connection.prepareStatement(sqlTransactions)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String accountId = rs.getString("account_id");
                double amount = rs.getDouble("amount");

                Date sqlDate = rs.getDate("creation_date");
                if (sqlDate == null) continue;

                LocalDate creationDate = sqlDate.toLocalDate();
                if (creationDate.isAfter(at)) continue;

                FinancialAccount account = accountMap.get(accountId);
                if (account == null) continue;

                if (account instanceof CashAccountEntity cash) {
                    cash.setAmount(cash.getAmount() + amount);
                } else if (account instanceof MobileBankingAccountEntity mobile) {
                    mobile.setAmount(mobile.getAmount() + amount);
                } else if (account instanceof BankAccountEntity bank) {
                    bank.setAmount(bank.getAmount() + amount);
                }
            }
        }

        return result;
    }

        public Map<String, Double> getEarnedAmountByMemberBetweenDates(
                String collectivityId,
                LocalDate from,
                LocalDate to
        ) throws Exception {
            String sql = """
            SELECT ct.member_debited_id, ct.amount
            FROM collectivity_transaction ct
            WHERE ct.collectivity_id = ?
              AND ct.creation_date BETWEEN ? AND ?
        """;

            Map<String, Double> earnedAmountByMember = new HashMap<>();

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, collectivityId);
                ps.setDate(2, java.sql.Date.valueOf(from));
                ps.setDate(3, java.sql.Date.valueOf(to));

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    String memberId = rs.getString("member_debited_id");
                    Double amount = rs.getDouble("amount");

                    earnedAmountByMember.put(memberId, earnedAmountByMember.getOrDefault(memberId, 0.0) + amount);
                }
            }

            return earnedAmountByMember;
        }

}