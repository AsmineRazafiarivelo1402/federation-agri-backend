package org.hei.federationagribackend.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private List<MemberEntity> referees = new ArrayList<>();
    private LocalDate creationDate;

    public MemberEntity() {
    }

    public MemberEntity(LocalDate creationDate, List<MemberEntity> referees, Boolean membershipDuesPaid, Boolean registrationFeePaid, MemberOccupation occupation, String memberIdentifier, String email, Integer phoneNumber, String profession, String address, Gender gender, LocalDate birthDate, String lastName, String firstName, String id) {
        this.creationDate = creationDate;
        this.referees = referees;
        this.membershipDuesPaid = membershipDuesPaid;
        this.registrationFeePaid = registrationFeePaid;
        this.occupation = occupation;
        this.memberIdentifier = memberIdentifier;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.profession = profession;
        this.address = address;
        this.gender = gender;
        this.birthDate = birthDate;
        this.lastName = lastName;
        this.firstName = firstName;
        this.id = id;
    }

    @Override
    public String toString() {
        return "MemberEntity{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", gender=" + gender +
                ", address='" + address + '\'' +
                ", profession='" + profession + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", email='" + email + '\'' +
                ", memberIdentifier='" + memberIdentifier + '\'' +
                ", occupation=" + occupation +
                ", registrationFeePaid=" + registrationFeePaid +
                ", membershipDuesPaid=" + membershipDuesPaid +
                ", referees=" + referees +
                ", creationDate=" + creationDate +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMemberIdentifier() {
        return memberIdentifier;
    }

    public void setMemberIdentifier(String memberIdentifier) {
        this.memberIdentifier = memberIdentifier;
    }

    public MemberOccupation getOccupation() {
        return occupation;
    }

    public void setOccupation(MemberOccupation occupation) {
        this.occupation = occupation;
    }

    public Boolean getRegistrationFeePaid() {
        return registrationFeePaid;
    }

    public void setRegistrationFeePaid(Boolean registrationFeePaid) {
        this.registrationFeePaid = registrationFeePaid;
    }

    public Boolean getMembershipDuesPaid() {
        return membershipDuesPaid;
    }

    public void setMembershipDuesPaid(Boolean membershipDuesPaid) {
        this.membershipDuesPaid = membershipDuesPaid;
    }

    public List<MemberEntity> getReferees() {
        return referees;
    }

    public void setReferees(List<MemberEntity> referees) {
        this.referees = referees;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}
