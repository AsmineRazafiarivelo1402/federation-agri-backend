package org.hei.federationagribackend.entity;

import java.time.LocalDate;


public class CollectivityTransactionEntity {

    private String id;
    private LocalDate creationDate;
    private Double amount;

    private PaymentMode paymentMode;

    private String memberDebitedId;
    private String collectivityId;

    private AccountType accountType;
    private String accountId;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public LocalDate getCreationDate() { return creationDate; }
    public void setCreationDate(LocalDate creationDate) { this.creationDate = creationDate; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public PaymentMode getPaymentMode() { return paymentMode; }
    public void setPaymentMode(PaymentMode paymentMode) { this.paymentMode = paymentMode; }

    public String getMemberDebitedId() { return memberDebitedId; }
    public void setMemberDebitedId(String memberDebitedId) { this.memberDebitedId = memberDebitedId; }

    public String getCollectivityId() { return collectivityId; }
    public void setCollectivityId(String collectivityId) { this.collectivityId = collectivityId; }

    public AccountType getAccountType() { return accountType; }
    public void setAccountType(AccountType accountType) { this.accountType = accountType; }

    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }
}