package org.hei.federationagribackend.service;

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

            // 🔥 VALIDATIONS OBLIGATOIRES
            if (dto.getLocation() == null || dto.getLocation().isBlank()) {
                throw new RuntimeException("Location is required");
            }

            if (dto.getMembers() == null || dto.getMembers().isEmpty()) {
                throw new RuntimeException("Members list cannot be empty");
            }

            if (dto.getStructure() == null) {
                throw new RuntimeException("Structure is required");
            }

            // 🔥 CREATE ENTITY
            CollectivityEntity c = new CollectivityEntity();
            c.setId(UUID.randomUUID().toString());
            c.setLocation(dto.getLocation());
            c.setFederationApproval(dto.getFederationApproval());

            // save collectivity
            repository.save(c);

            // members
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

            // structure validation
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
}