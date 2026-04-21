package org.hei.federationagribackend.entity;

import java.util.Objects;

public class MemberRefereeEntity {
    private Integer id;
    private MemberEntity member;
    private MemberEntity memberReferee;

    public MemberRefereeEntity(Integer id, MemberEntity member, MemberEntity memberReferee) {
        this.id = id;
        this.member = member;
        this.memberReferee = memberReferee;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MemberEntity getMember() {
        return member;
    }

    public void setMember(MemberEntity member) {
        this.member = member;
    }

    public MemberEntity getMemberReferee() {
        return memberReferee;
    }

    public void setMemberReferee(MemberEntity memberReferee) {
        this.memberReferee = memberReferee;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MemberRefereeEntity that = (MemberRefereeEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(member, that.member) && Objects.equals(memberReferee, that.memberReferee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, member, memberReferee);
    }

    @Override
    public String toString() {
        return "MemberRefereeEntity{" +
                "id=" + id +
                ", member=" + member +
                ", memberReferee=" + memberReferee +
                '}';
    }
}
