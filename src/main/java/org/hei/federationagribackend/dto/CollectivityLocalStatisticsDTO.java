package org.hei.federationagribackend.dto;

public class CollectivityLocalStatisticsDTO {
    private MemberDTO memberDescription;
    private Double earnedAmount;
    private Double unpaidAmount;

    public CollectivityLocalStatisticsDTO() {}

    public CollectivityLocalStatisticsDTO(MemberDTO memberDescription, Double earnedAmount, Double unpaidAmount) {
        this.memberDescription = memberDescription;
        this.earnedAmount = earnedAmount;
        this.unpaidAmount = unpaidAmount;
    }

    public MemberDTO getMemberDescription() { return memberDescription; }
    public void setMemberDescription(MemberDTO memberDescription) { this.memberDescription = memberDescription; }
    public Double getEarnedAmount() { return earnedAmount; }
    public void setEarnedAmount(Double earnedAmount) { this.earnedAmount = earnedAmount; }
    public Double getUnpaidAmount() { return unpaidAmount; }
    public void setUnpaidAmount(Double unpaidAmount) { this.unpaidAmount = unpaidAmount; }
}