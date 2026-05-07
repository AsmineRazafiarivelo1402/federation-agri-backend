package org.hei.federationagribackend.dto;

public class CollectivityOverallStatisticsDTO {
    private CollectivityInformationDTO collectivityInformation;
    private Integer newMembersNumber;
    private Double overallMemberCurrentDuePercentage;

    public CollectivityOverallStatisticsDTO() {}

    public CollectivityInformationDTO getCollectivityInformation() { return collectivityInformation; }
    public void setCollectivityInformation(CollectivityInformationDTO collectivityInformation) { this.collectivityInformation = collectivityInformation; }
    public Integer getNewMembersNumber() { return newMembersNumber; }
    public void setNewMembersNumber(Integer newMembersNumber) { this.newMembersNumber = newMembersNumber; }
    public Double getOverallMemberCurrentDuePercentage() { return overallMemberCurrentDuePercentage; }
    public void setOverallMemberCurrentDuePercentage(Double overallMemberCurrentDuePercentage) { this.overallMemberCurrentDuePercentage = overallMemberCurrentDuePercentage; }
}