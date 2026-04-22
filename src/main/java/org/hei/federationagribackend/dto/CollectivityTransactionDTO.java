package org.hei.federationagribackend.dto;

import org.hei.federationagribackend.entity.PaymentMode;

import java.time.LocalDate;

public class CollectivityTransactionDTO {

    private String id;
    private LocalDate creationDate;
    private Double amount;
    private PaymentMode paymentMode;

    private FinancialAccountDTO accountCredited;
    private MemberDTO memberDebited;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public LocalDate getCreationDate() { return creationDate; }
    public void setCreationDate(LocalDate creationDate) { this.creationDate = creationDate; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public PaymentMode getPaymentMode() { return paymentMode; }
    public void setPaymentMode(PaymentMode paymentMode) { this.paymentMode = paymentMode; }

    public FinancialAccountDTO getAccountCredited() { return accountCredited; }
    public void setAccountCredited(FinancialAccountDTO accountCredited) { this.accountCredited = accountCredited; }

    public MemberDTO getMemberDebited() { return memberDebited; }
    public void setMemberDebited(MemberDTO memberDebited) { this.memberDebited = memberDebited; }
}
