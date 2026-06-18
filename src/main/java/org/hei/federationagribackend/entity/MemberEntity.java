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

    public MemberEntity(String id, String firstName, String lastName, LocalDate birthDate, Gender gender, String address, String profession, Integer phoneNumber, String email, String memberIdentifier, MemberOccupation occupation, Boolean registrationFeePaid, Boolean membershipDuesPaid) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.address = address;
        this.profession = profession;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.memberIdentifier = memberIdentifier;
        this.occupation = occupation;
        this.registrationFeePaid = registrationFeePaid;
        this.membershipDuesPaid = membershipDuesPaid;
    }

    public MemberEntity() {

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MemberEntity that = (MemberEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(birthDate, that.birthDate) && gender == that.gender && Objects.equals(address, that.address) && Objects.equals(profession, that.profession) && Objects.equals(phoneNumber, that.phoneNumber) && Objects.equals(email, that.email) && Objects.equals(memberIdentifier, that.memberIdentifier) && occupation == that.occupation && Objects.equals(registrationFeePaid, that.registrationFeePaid) && Objects.equals(membershipDuesPaid, that.membershipDuesPaid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, birthDate, gender, address, profession, phoneNumber, email, memberIdentifier, occupation, registrationFeePaid, membershipDuesPaid);
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
                '}';
    }


}
