package org.hei.federationagribackend.service;

import org.hei.federationagribackend.dto.CollectivityLocalStatisticsDTO;
import org.hei.federationagribackend.dto.MemberDTO;
import org.hei.federationagribackend.entity.Frequency;
import org.hei.federationagribackend.entity.MemberShipFeeEntity;
import org.hei.federationagribackend.entity.MemberShipFeeEntity;
import org.hei.federationagribackend.repository.CollectivityTransactionRepository;
import org.hei.federationagribackend.repository.MemberRepository;
import org.hei.federationagribackend.repository.MemberShipFeeRepository;
import org.hei.federationagribackend.repository.MemberShipFeeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
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

        List<MemberDTO> members = memberRepository.findMemberDTOsByCollectivityId(collectivityId);

        Map<String, Double> earnedAmountByMember = transactionRepository.getEarnedAmountByMemberBetweenDates(collectivityId, from, to);

        List<MemberShipFeeEntity> activeFees = membershipFeeRepository.findActiveFeesByCollectivityId(collectivityId);

        List<CollectivityLocalStatisticsDTO> result = new ArrayList<>();

        for (MemberDTO member : members) {
            String memberId = member.getId();

            Double earnedAmount = earnedAmountByMember.getOrDefault(memberId, 0.0);

            Double unpaidAmount = calculateUnpaidAmountForMember(memberId, activeFees, from, to);

            CollectivityLocalStatisticsDTO stats = new CollectivityLocalStatisticsDTO(member, earnedAmount, unpaidAmount);
            result.add(stats);
        }

        return result;
    }

    private Double calculateUnpaidAmountForMember(
            String memberId,
            List<MemberShipFeeEntity> activeFees,
            LocalDate from,
            LocalDate to
    ) {
        double totalUnpaid = 0.0;

        for (MemberShipFeeEntity fee : activeFees) {
            LocalDate eligibleFrom = fee.getEligibleFrom();

            if (eligibleFrom.isAfter(to)) {
                continue;
            }

            LocalDate startDate = eligibleFrom.isBefore(from) ? from : eligibleFrom;
            if (startDate.isAfter(to)) {
                continue;
            }

            int numberOfInstallments = calculateNumberOfInstallments(startDate, to, fee.getFrequency());

            totalUnpaid += numberOfInstallments * fee.getAmount();
        }

        return totalUnpaid;
    }

    private int calculateNumberOfInstallments(LocalDate start, LocalDate end, Frequency frequency) {
        if (start.isAfter(end)) {
            return 0;
        }

        switch (frequency) {
            case WEEKLY:
                long weeks = Period.between(start, end).getDays() / 7;
                return (int) weeks + 1;
            case MONTHLY:
                long months = Period.between(start, end).toTotalMonths();
                return (int) months + 1;
            case ANNUALLY:
                long years = Period.between(start, end).getYears();
                return (int) years + 1;
            case PUNCTUALLY:
                return 1;
            default:
                return 0;
        }
    }
}