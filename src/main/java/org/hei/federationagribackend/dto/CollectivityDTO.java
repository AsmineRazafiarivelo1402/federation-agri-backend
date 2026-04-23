package org.hei.federationagribackend.dto;

import java.util.List;
public class CollectivityDTO {
    private String id;
    private String name;
    private String number;
    private String location;
    private Boolean federationApproval;

    private List<MemberDTO> members;
    private CollectivityStructureDTO structure;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getFederationApproval() {
        return federationApproval;
    }

    public void setFederationApproval(Boolean federationApproval) {
        this.federationApproval = federationApproval;
    }

    public List<MemberDTO> getMembers() {
        return members;
    }

    public void setMembers(List<MemberDTO> members) {
        this.members = members;
    }

    public CollectivityStructureDTO getStructure() {
        return structure;
    }

    public void setStructure(CollectivityStructureDTO structure) {
        this.structure = structure;
    }
}