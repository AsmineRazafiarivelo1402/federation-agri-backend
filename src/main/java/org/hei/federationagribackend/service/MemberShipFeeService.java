package org.hei.federationagribackend.service;

import org.hei.federationagribackend.dto.MemberShipFeeDTO;
import org.hei.federationagribackend.entity.MemberShipFeeEntity;

import org.hei.federationagribackend.mapper.MembershipFeeMapper;
import org.hei.federationagribackend.repository.MemberShipFeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberShipFeeService {

    private final MemberShipFeeRepository repository;

    public MemberShipFeeService(MemberShipFeeRepository repository) {
        this.repository = repository;
    }

    public List<MemberShipFeeDTO> create(String collectivityId, List<MemberShipFeeDTO> dtos) {
        List<MemberShipFeeEntity> entities = dtos.stream()
                .map(MembershipFeeMapper::toEntity)
                .toList();

        entities.forEach(e -> {
            if (e.getAmount() < 0) {
                throw new RuntimeException("Invalid amount");
            }
        });

        List<MemberShipFeeEntity> saved = repository.saveAll(collectivityId, entities);

        return saved.stream()
                .map(MembershipFeeMapper::toDTO)
                .toList();
    }

    public List<MemberShipFeeDTO> getAll(String collectivityId) {
        return repository.findByCollectivityId(collectivityId)
                .stream()
                .map(MembershipFeeMapper::toDTO)
                .toList();
    }
}