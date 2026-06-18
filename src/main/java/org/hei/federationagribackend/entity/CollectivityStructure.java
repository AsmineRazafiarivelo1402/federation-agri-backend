package org.hei.federationagribackend.entity;

import java.util.Objects;

public class CollectivityStructure {
    private MemberEntity president;
    private MemberEntity vicePresident;
    private MemberEntity treasurer;
    private MemberEntity secretary;

    private String presidentId;
    private String vicePresidentId;
    private String treasurerId;
    private String secretaryId;

    public CollectivityStructure() {
    }

    public CollectivityStructure(MemberEntity president, MemberEntity vicePresident, MemberEntity treasurer, MemberEntity secretary, String presidentId, String vicePresidentId, String treasurerId, String secretaryId) {
        this.president = president;
        this.vicePresident = vicePresident;
        this.treasurer = treasurer;
        this.secretary = secretary;
        this.presidentId = presidentId;
        this.vicePresidentId = vicePresidentId;
        this.treasurerId = treasurerId;
        this.secretaryId = secretaryId;
    }

    public MemberEntity getPresident() {
        return president;
    }

    public void setPresident(MemberEntity president) {
        this.president = president;
    }

    public MemberEntity getVicePresident() {
        return vicePresident;
    }

    public void setVicePresident(MemberEntity vicePresident) {
        this.vicePresident = vicePresident;
    }

    public MemberEntity getTreasurer() {
        return treasurer;
    }

    public void setTreasurer(MemberEntity treasurer) {
        this.treasurer = treasurer;
    }

    public MemberEntity getSecretary() {
        return secretary;
    }

    public void setSecretary(MemberEntity secretary) {
        this.secretary = secretary;
    }

    public String getPresidentId() {
        return presidentId;
    }

    public void setPresidentId(String presidentId) {
        this.presidentId = presidentId;
    }

    public String getVicePresidentId() {
        return vicePresidentId;
    }

    public void setVicePresidentId(String vicePresidentId) {
        this.vicePresidentId = vicePresidentId;
    }

    public String getTreasurerId() {
        return treasurerId;
    }

    public void setTreasurerId(String treasurerId) {
        this.treasurerId = treasurerId;
    }

    public String getSecretaryId() {
        return secretaryId;
    }

    public void setSecretaryId(String secretaryId) {
        this.secretaryId = secretaryId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CollectivityStructure that = (CollectivityStructure) o;
        return Objects.equals(president, that.president) && Objects.equals(vicePresident, that.vicePresident) && Objects.equals(treasurer, that.treasurer) && Objects.equals(secretary, that.secretary) && Objects.equals(presidentId, that.presidentId) && Objects.equals(vicePresidentId, that.vicePresidentId) && Objects.equals(treasurerId, that.treasurerId) && Objects.equals(secretaryId, that.secretaryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(president, vicePresident, treasurer, secretary, presidentId, vicePresidentId, treasurerId, secretaryId);
    }

    @Override
    public String toString() {
        return "CollectivityStructure{" +
                "president=" + president +
                ", vicePresident=" + vicePresident +
                ", treasurer=" + treasurer +
                ", secretary=" + secretary +
                ", presidentId='" + presidentId + '\'' +
                ", vicePresidentId='" + vicePresidentId + '\'' +
                ", treasurerId='" + treasurerId + '\'' +
                ", secretaryId='" + secretaryId + '\'' +
                '}';
    }
}
