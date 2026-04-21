package org.hei.federationagribackend.entity;

import java.time.LocalDate;

public class MemberEntity {
    private String id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Gender gender;
    private String address;
    private String profession;
    private Integer phoneNumber;
    private String email;
    private String memberIdentifier;
    private MemberOccupation occupation;
    private Boolean registrationFeePaid;
    private Boolean membershipDuesPaid;
}
