package org.hei.federationagribackend.entity;


import org.hei.federationagribackend.Interface.FinancialAccount;

public class MobileBankingAccountEntity implements FinancialAccount {

    private String id;
    private String holderName;
    private MobileBankingService mobileBankingService;
    private Long mobileNumber;
    private Double amount;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getHolderName() { return holderName; }
    public void setHolderName(String holderName) { this.holderName = holderName; }

    public MobileBankingService getMobileBankingService() { return mobileBankingService; }
    public void setMobileBankingService(MobileBankingService mobileBankingService) { this.mobileBankingService = mobileBankingService; }

    public Long getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(Long mobileNumber) { this.mobileNumber = mobileNumber; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
}

