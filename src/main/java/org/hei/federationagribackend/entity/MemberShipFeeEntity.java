package org.hei.federationagribackend.entity;

import java.time.LocalDate;

public class MemberShipFeeEntity {
    private String id;
    private String collectivityId;
    private LocalDate eligibleFrom;
    private String frequency;
    private Double amount;
    private String label;
    private String status;
}
