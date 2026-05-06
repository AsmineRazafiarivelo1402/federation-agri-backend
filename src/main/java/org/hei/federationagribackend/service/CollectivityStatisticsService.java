package org.hei.federationagribackend.service;

import org.hei.federationagribackend.dto.CollectivityLocalStatisticsDTO;
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

        // 1. Récupérer tous les membres de la collectivité
        List<MemberDTO> members = memberRepository.findMemberDTOsByCollectivityId(collectivityId);

        // 2. Récupérer les montants encaissés par membre sur la période
        Map<String, Double> earnedAmountByMember = transactionRepository.getEarnedAmountByMemberBetweenDates(collectivityId, from, to);

        // 3. Récupérer le montant théorique total dû par membre (cotisations actives)
        double theoreticalAmountPerMember = membershipFeeRepository.getTotalTheoreticalAmountForPeriod(collectivityId, from, to);

        // 4. Construire la réponse avec le reste à payer
        List<CollectivityLocalStatisticsDTO> result = new ArrayList<>();

        for (MemberDTO member : members) {
            String memberId = member.getId();

            // Montant déjà payé par le membre
            Double earnedAmount = earnedAmountByMember.getOrDefault(memberId, 0.0);

            // Montant restant à payer = montant théorique - montant déjà payé
            // Si le résultat est négatif, le reste à payer est 0
            Double remainingToPay = theoreticalAmountPerMember - earnedAmount;
            if (remainingToPay < 0) remainingToPay = 0.0;

            CollectivityLocalStatisticsDTO stats = new CollectivityLocalStatisticsDTO();
            stats.setMemberDescription(member);
            stats.setEarnedAmount(earnedAmount);
            stats.setUnpaidAmount(remainingToPay);  // ← Reste à payer (unpaid = non encore payé)

            result.add(stats);
        }

        return result;
    }
}