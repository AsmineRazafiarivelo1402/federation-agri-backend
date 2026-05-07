package org.hei.federationagribackend.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "memberDescription", "earnedAmount", "unpaidAmount", "assiduityPercentage" })
public class CollectivityLocalStatisticsDTO {
    private MemberDescriptionDTO memberDescription;
    private Double earnedAmount;
    private Double unpaidAmount;
    private Double assiduityPercentage;

    public CollectivityLocalStatisticsDTO() {}

    public CollectivityLocalStatisticsDTO(MemberDescriptionDTO memberDescription,
                                          Double earnedAmount,
                                          Double unpaidAmount,
                                          Double assiduityPercentage) {
        this.memberDescription = memberDescription;
        this.earnedAmount = earnedAmount;
        this.unpaidAmount = unpaidAmount;
        this.assiduityPercentage = assiduityPercentage;
    }

    // Getters & Setters
    public MemberDescriptionDTO getMemberDescription() {
        return memberDescription;
    }

    public void setMemberDescription(MemberDescriptionDTO memberDescription) {
        this.memberDescription = memberDescription;
    }

    public Double getEarnedAmount() {
        return earnedAmount;
    }

    public void setEarnedAmount(Double earnedAmount) {
        this.earnedAmount = earnedAmount;
    }

    public Double getUnpaidAmount() {
        return unpaidAmount;
    }

    public void setUnpaidAmount(Double unpaidAmount) {
        this.unpaidAmount = unpaidAmount;
    }

    public Double getAssiduityPercentage() {
        return assiduityPercentage;
    }

    public void setAssiduityPercentage(Double assiduityPercentage) {
        this.assiduityPercentage = assiduityPercentage;
    }
}