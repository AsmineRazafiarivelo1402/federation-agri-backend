package org.hei.federationagribackend.mapper;

import org.hei.federationagribackend.dto.CreateMemberDTO;
import org.hei.federationagribackend.dto.MemberDTO;
import org.hei.federationagribackend.entity.MemberEntity;

public class MemberMapper {

    public static MemberEntity toEntity(CreateMemberDTO dto) {
        MemberEntity entity = new MemberEntity();

        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setBirthDate(dto.getBirthDate());
        entity.setGender(dto.getGender());
        entity.setAddress(dto.getAddress());
        entity.setProfession(dto.getProfession());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setEmail(dto.getEmail());
        entity.setOccupation(dto.getOccupation());
        entity.setRegistrationFeePaid(dto.getRegistrationFeePaid());
        entity.setMembershipDuesPaid(dto.getMembershipDuesPaid());
        entity.setCreationDate(dto.getDate("creation_date").toLocalDate());

        return entity;
    }

    public static MemberDTO toDTO(MemberEntity entity) {
        MemberDTO dto = new MemberDTO();

        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setBirthDate(entity.getBirthDate());
        dto.setGender(entity.getGender());
        dto.setAddress(entity.getAddress());
        dto.setProfession(entity.getProfession());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setEmail(entity.getEmail());
        dto.setOccupation(entity.getOccupation());

        return dto;
    }
}
