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

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<MemberDTO> createMembers(List<CreateMemberDTO> requests) {

        List<MemberDTO> result = new ArrayList<>();

        for (CreateMemberDTO dto : requests) {

            // ❌ 400
            if (!Boolean.TRUE.equals(dto.getRegistrationFeePaid())
                    || !Boolean.TRUE.equals(dto.getMembershipDuesPaid())) {
                throw new BadRequestException("Payment not completed");
            }

            // DTO → ENTITY
            MemberEntity entity = MemberMapper.toEntity(dto);

            // SAVE
            entity = memberRepository.save(entity);

            // REFEREES
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
