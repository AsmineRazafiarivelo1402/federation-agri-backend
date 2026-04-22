package org.hei.federationagribackend.mapper;


import org.hei.federationagribackend.dto.MemberShipFeeDTO;
import org.hei.federationagribackend.entity.MemberShipFeeEntity;

public class MembershipFeeMapper {

    // 🔹 Entity → DTO
    public static MemberShipFeeDTO toDTO(MemberShipFeeEntity entity) {
        MemberShipFeeDTO dto = new MemberShipFeeDTO();

        dto.setId(entity.getId());
        dto.setEligibleFrom(entity.getEligibleFrom());
        dto.setFrequency(entity.getFrequency());
        dto.setAmount(entity.getAmount());
        dto.setLabel(entity.getLabel());
        dto.setStatus(entity.getStatus());

        return dto;
    }

    // 🔹 DTO → Entity
    public static MemberShipFeeEntity toEntity(MemberShipFeeDTO dto) {
        MemberShipFeeEntity entity = new MemberShipFeeEntity();

        entity.setId(dto.getId());
        entity.setEligibleFrom(dto.getEligibleFrom());
        entity.setFrequency(dto.getFrequency());
        entity.setAmount(dto.getAmount());
        entity.setLabel(dto.getLabel());
        entity.setStatus(dto.getStatus());

        return entity;
    }
}
