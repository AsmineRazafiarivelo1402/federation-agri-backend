package org.hei.federationagribackend.service;

import org.hei.federationagribackend.dto.AssignCollectivityIdentityDTO;
import org.hei.federationagribackend.dto.CreateCollectivityDTO;
import org.hei.federationagribackend.dto.CreateCollectivityStructureDTO;
import org.hei.federationagribackend.entity.CollectivityEntity;
import org.hei.federationagribackend.entity.CollectivityStructure;
import org.hei.federationagribackend.entity.MemberEntity;
import org.hei.federationagribackend.repository.CollectivityRepository;
import org.hei.federationagribackend.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class CollectivityService {

    private final CollectivityRepository repository;
    private final MemberRepository memberRepository;

    public CollectivityService(CollectivityRepository repository,
                               MemberRepository memberRepository) {
        this.repository = repository;
        this.memberRepository = memberRepository;
    }

    public List<CollectivityEntity> create(List<CreateCollectivityDTO> dtos) {

        List<CollectivityEntity> result = new ArrayList<>();

        for (CreateCollectivityDTO dto : dtos) {

            if (dto.getLocation() == null || dto.getLocation().isBlank()) {
                throw new RuntimeException("Location is required");
            }

            if (dto.getMembers() == null || dto.getMembers().isEmpty()) {
                throw new RuntimeException("Members list cannot be empty");
            }

            if (dto.getStructure() == null) {
                throw new RuntimeException("Structure is required");
            }

            CollectivityEntity c = new CollectivityEntity();
            c.setId(UUID.randomUUID().toString());
            c.setLocation(dto.getLocation());
            c.setFederationApproval(dto.getFederationApproval());

            repository.save(c);

            List<MemberEntity> members = new ArrayList<>();

            for (String memberId : dto.getMembers()) {

                MemberEntity m = memberRepository.findById(memberId);

                if (m == null) {
                    throw new RuntimeException("Member not found: " + memberId);
                }

                repository.saveMemberRelation(c.getId(), memberId);

                members.add(m);
            }

            c.setMembers(members);

            CreateCollectivityStructureDTO s = dto.getStructure();

            if (s.getPresident() == null ||
                    s.getVicePresident() == null ||
                    s.getTreasurer() == null ||
                    s.getSecretary() == null) {

                throw new RuntimeException("Structure incomplete");
            }

            CollectivityStructure structure = new CollectivityStructure(
                    null,
                    null,
                    null,
                    null,
                    s.getPresident(),
                    s.getVicePresident(),
                    s.getTreasurer(),
                    s.getSecretary()
            );

            repository.saveStructure(c.getId(), structure);

            c.setStructure(structure);

            result.add(c);
        }

        return result;
    }
    public CollectivityEntity assignIdentity(String id, AssignCollectivityIdentityDTO dto) {


        CollectivityEntity c = repository.findById(id);
        if (c == null) {
            throw new RuntimeException("Collectivity not found");
        }


        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new RuntimeException("Invalid name");
        }

        if (dto.getNumber() == null || dto.getNumber().isBlank()) {
            throw new RuntimeException("Invalid number");
        }


        if (repository.existsByName(dto.getName())) {
            throw new RuntimeException("Name already exists");
        }

        if (repository.existsByNumber(dto.getNumber())) {
            throw new RuntimeException("Number already exists");
        }


        repository.updateIdentity(id, dto.getName(), dto.getNumber());

        return repository.findById(id);
    }
}