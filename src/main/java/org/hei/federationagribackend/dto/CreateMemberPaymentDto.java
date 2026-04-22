package org.hei.federationagribackend.dto;

public class CreateMemberPaymentDto {
    private int amount;
    private String membershipFeeIdentifier;
    private String accountCreditedIdentifier;
    private String paymentMode;

    public CreateMemberPaymentDto() {}

    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }
    public String getMembershipFeeIdentifier() { return membershipFeeIdentifier; }
    public void setMembershipFeeIdentifier(String id) { this.membershipFeeIdentifier = id; }
    public String getAccountCreditedIdentifier() { return accountCreditedIdentifier; }
    public void setAccountCreditedIdentifier(String id) { this.accountCreditedIdentifier = id; }
    public String getPaymentMode() { return paymentMode; }
    public void setPaymentMode(String mode) { this.paymentMode = mode; }
}
