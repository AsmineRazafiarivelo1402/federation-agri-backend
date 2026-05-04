package org.hei.federationagribackend.entity;


import org.hei.federationagribackend.Interface.FinancialAccount;

public class CashAccountEntity implements FinancialAccount {

    private String id;
    private Double amount;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }


}
