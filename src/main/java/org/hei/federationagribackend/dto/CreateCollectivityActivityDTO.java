package org.hei.federationagribackend.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class CreateCollectivityActivityDTO {
    private String label;
    private String activityType;
    private List<String> memberOccupationConcerned;
    private MonthlyRecurrenceRuleDTO recurrenceRule;
    private LocalDate executiveDate;

    public CreateCollectivityActivityDTO() {
    }

    public CreateCollectivityActivityDTO(String label, String activityType, List<String> memberOccupationConcerned, MonthlyRecurrenceRuleDTO recurrenceRule, LocalDate executiveDate) {
        this.label = label;
        this.activityType = activityType;
        this.memberOccupationConcerned = memberOccupationConcerned;
        this.recurrenceRule = recurrenceRule;
        this.executiveDate = executiveDate;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public void setMemberOccupationConcerned(List<String> memberOccupationConcerned) {
        this.memberOccupationConcerned = memberOccupationConcerned;
    }

    public void setRecurrenceRule(MonthlyRecurrenceRuleDTO recurrenceRule) {
        this.recurrenceRule = recurrenceRule;
    }

    public void setExecutiveDate(LocalDate executiveDate) {
        this.executiveDate = executiveDate;
    }
}
