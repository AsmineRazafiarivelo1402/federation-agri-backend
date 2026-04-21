package org.hei.federationagribackend.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CollectivityEntity {
    private String id;
    private String location;
    private Boolean federationApproval;


    private List<MemberEntity> members = new ArrayList<>();

    private CollectivityStructure structure;

    public CollectivityEntity() {
    }

    public CollectivityEntity(String id, String location, Boolean federationApproval, List<MemberEntity> members, CollectivityStructure structure) {
        this.id = id;
        this.location = location;
        this.federationApproval = federationApproval;
        this.members = members;
        this.structure = structure;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<MemberEntity> getMembers() {
        return members;
    }

    public void setMembers(List<MemberEntity> members) {
        this.members = members;
    }

    public CollectivityStructure getStructure() {
        return structure;
    }

    public void setStructure(CollectivityStructure structure) {
        this.structure = structure;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CollectivityEntity that = (CollectivityEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(location, that.location) && Objects.equals(federationApproval, that.federationApproval) && Objects.equals(members, that.members) && Objects.equals(structure, that.structure);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, location, federationApproval, members, structure);
    }

    @Override
    public String toString() {
        return "CollectivityEntity{" +
                "id='" + id + '\'' +
                ", location='" + location + '\'' +
                ", federationApproval=" + federationApproval +
                ", members=" + members +
                ", structure=" + structure +
                '}';
    }
}
