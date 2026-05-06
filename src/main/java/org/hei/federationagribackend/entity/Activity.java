package org.hei.federationagribackend.entity;

import java.time.LocalDate;

public class Activity {
    private String id;
    private String collectivityId;
    private String label;
    private String activityType; // MEETING, TRAINING, OTHER

    // Pour la date fixe
    private LocalDate executiveDate;

    // Pour la récurrence (on "aplatit" l'objet ici pour le SQL)
    private Integer weekOrdinal;
    private String dayOfWeek;

    // Constructeurs
    public Activity() {}

    public Activity(String id, String collectivityId, String label, String activityType, LocalDate executiveDate, Integer weekOrdinal, String dayOfWeek) {
        this.id = id;
        this.collectivityId = collectivityId;
        this.label = label;
        this.activityType = activityType;
        this.executiveDate = executiveDate;
        this.weekOrdinal = weekOrdinal;
        this.dayOfWeek = dayOfWeek;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCollectivityId() {
        return collectivityId;
    }

    public void setCollectivityId(String collectivityId) {
        this.collectivityId = collectivityId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public LocalDate getExecutiveDate() {
        return executiveDate;
    }

    public void setExecutiveDate(LocalDate executiveDate) {
        this.executiveDate = executiveDate;
    }

    public Integer getWeekOrdinal() {
        return weekOrdinal;
    }

    public void setWeekOrdinal(Integer weekOrdinal) {
        this.weekOrdinal = weekOrdinal;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
