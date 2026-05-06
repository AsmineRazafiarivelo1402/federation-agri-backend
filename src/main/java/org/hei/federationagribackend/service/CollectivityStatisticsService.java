package org.hei.federationagribackend.service;

import org.hei.federationagribackend.dto.CollectivityLocalStatisticsDTO;
import org.hei.federationagribackend.dto.MemberDescriptionDTO;
import org.hei.federationagribackend.dto.MemberDTO;
import org.hei.federationagribackend.repository.CollectivityTransactionRepository;
import org.hei.federationagribackend.repository.MemberRepository;
import org.hei.federationagribackend.repository.MemberShipFeeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CollectivityStatisticsService {

    private final CollectivityTransactionRepository transactionRepository;
    private final MemberRepository memberRepository;
    private final MemberShipFeeRepository membershipFeeRepository;

    public CollectivityStatisticsService(
            CollectivityTransactionRepository transactionRepository,
            MemberRepository memberRepository,
            MemberShipFeeRepository membershipFeeRepository
    ) {
        this.transactionRepository = transactionRepository;
        this.memberRepository = memberRepository;
        this.membershipFeeRepository = membershipFeeRepository;
    }

    public List<CollectivityLocalStatisticsDTO> getCollectivityLocalStatistics(
            String collectivityId,
            LocalDate from,
            LocalDate to
    ) throws Exception {
        if (from.isAfter(to)) {
            throw new IllegalArgumentException("'from' date must be before or equal to 'to' date");
        }

        List<MemberDTO> members = memberRepository.findMemberDTOsByCollectivityId(collectivityId);
        Map<String, Double> earnedAmountByMember = transactionRepository.getEarnedAmountByMemberBetweenDates(collectivityId, from, to);
        double theoreticalAmountPerMember = membershipFeeRepository.getTotalTheoreticalAmountForPeriod(collectivityId, from, to);

        List<CollectivityLocalStatisticsDTO> result = new ArrayList<>();

        for (MemberDTO member : members) {
            String memberId = member.getId();

            // Utiliser MemberDescriptionDTO avec seulement 5 champs
            MemberDescriptionDTO memberDescription = new MemberDescriptionDTO();
            memberDescription.setId(member.getId());
            memberDescription.setFirstName(member.getFirstName());
            memberDescription.setLastName(member.getLastName());
            memberDescription.setEmail(member.getEmail());
            memberDescription.setOccupation(member.getOccupation().name());

            Double earnedAmount = earnedAmountByMember.getOrDefault(memberId, 0.0);
            Double remainingToPay = theoreticalAmountPerMember - earnedAmount;
            if (remainingToPay < 0) remainingToPay = 0.0;

            CollectivityLocalStatisticsDTO stats = new CollectivityLocalStatisticsDTO();
            stats.setMemberDescription(memberDescription);
            stats.setEarnedAmount(earnedAmount);
            stats.setUnpaidAmount(remainingToPay);

            result.add(stats);
        }

        return result;
    }
    public boolean collectivityExists(String collectivityId) throws Exception {
        return membershipFeeRepository.collectivityExists(collectivityId);
    }

}