package org.hei.federationagribackend.entity;


import org.hei.federationagribackend.Interface.FinancialAccount;

public class BankAccountEntity implements FinancialAccount {

    private String id;
    private String holderName;
    private Bank bankName;
    private Integer bankCode;
    private Integer bankBranchCode;
    private Long bankAccountNumber;
    private Integer bankAccountKey;
    private Double amount;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getHolderName() { return holderName; }
    public void setHolderName(String holderName) { this.holderName = holderName; }

    public Bank getBankName() { return bankName; }
    public void setBankName(Bank bankName) { this.bankName = bankName; }

    public Integer getBankCode() { return bankCode; }
    public void setBankCode(Integer bankCode) { this.bankCode = bankCode; }

    public Integer getBankBranchCode() { return bankBranchCode; }
    public void setBankBranchCode(Integer bankBranchCode) { this.bankBranchCode = bankBranchCode; }

    public Long getBankAccountNumber() { return bankAccountNumber; }
    public void setBankAccountNumber(Long bankAccountNumber) { this.bankAccountNumber = bankAccountNumber; }

    public Integer getBankAccountKey() { return bankAccountKey; }
    public void setBankAccountKey(Integer bankAccountKey) { this.bankAccountKey = bankAccountKey; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
}
