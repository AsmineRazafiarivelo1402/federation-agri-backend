package org.hei.federationagribackend.dto;

import org.hei.federationagribackend.entity.Gender;
import org.hei.federationagribackend.entity.MemberOccupation;

import java.time.LocalDate;
import java.util.List;

public class CreateMemberDTO {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Gender gender;
    private String address;
    private String profession;
    private Integer phoneNumber;
    private String email;
    private MemberOccupation occupation;

    private String collectivityIdentifier;
    private List<String> referees;

    private Boolean registrationFeePaid;
    private Boolean membershipDuesPaid;
}