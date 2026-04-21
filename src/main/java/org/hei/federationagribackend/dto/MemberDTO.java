package org.hei.federationagribackend.dto;

import org.hei.federationagribackend.entity.Gender;
import org.hei.federationagribackend.entity.MemberOccupation;

import java.time.LocalDate;
import java.util.List;

public class MemberDTO {
    private String id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Gender gender;
    private String address;
    private String profession;
    private Integer phoneNumber;
    private String email;
    private MemberOccupation occupation;

    private List<MemberDTO> referees;
}
