package org.hei.federationagribackend.entity;

import org.hei.federationagribackend.dto.CreateMemberPaymentDto;

import java.time.LocalDate;

public class MemberPayment {
    private String id;
    private String memberId;
    private int amount;
    private String feeId;
    private String accountId;
    private String paymentMode;
    private LocalDate creationDate;

    public MemberPayment(String id, String memberId, CreateMemberPaymentDto dto) {
        this.id = id;
        this.memberId = memberId;
        this.amount = dto.getAmount();
        this.feeId = dto.getMembershipFeeIdentifier();
        this.accountId = dto.getAccountCreditedIdentifier();
        this.paymentMode = dto.getPaymentMode();
        this.creationDate = LocalDate.now();
    }

    public String getId() { return id; }
    public String getMemberId() { return memberId; }
    public int getAmount() { return amount; }
    public String getFeeId() { return feeId; }
    public String getAccountId() { return accountId; }
    public String getPaymentMode() { return paymentMode; }
    public LocalDate getCreationDate() { return creationDate; }
}
