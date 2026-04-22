package org.hei.federationagribackend.service;

import org.hei.federationagribackend.dto.CreateMemberDTO;
import org.hei.federationagribackend.dto.MemberDTO;
import org.hei.federationagribackend.entity.MemberEntity;
import org.hei.federationagribackend.exception.BadRequestException;
import org.hei.federationagribackend.mapper.MemberMapper;
import org.hei.federationagribackend.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<MemberDTO> createMembers(List<CreateMemberDTO> requests) {

        List<MemberDTO> result = new ArrayList<>();

        for (CreateMemberDTO dto : requests) {

            if (!Boolean.TRUE.equals(dto.getRegistrationFeePaid())
                    || !Boolean.TRUE.equals(dto.getMembershipDuesPaid())) {
                throw new BadRequestException("Payment not completed");
            }

            MemberEntity entity = MemberMapper.toEntity(dto);

            entity.setId(UUID.randomUUID().toString());

            entity = memberRepository.save(entity, dto.getCollectivityIdentifier());

            List<MemberEntity> referees = new ArrayList<>();

            if (dto.getReferees() != null) {
                for (String refId : dto.getReferees()) {
                    referees.add(memberRepository.findById(refId));
                }
            }

            entity.setReferees(referees);

            result.add(MemberMapper.toDTO(entity));
        }

        return result;
    }
}