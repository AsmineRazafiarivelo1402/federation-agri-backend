package org.hei.federationagribackend.service;

import org.hei.federationagribackend.dto.CollectivityInformationDTO;
import org.hei.federationagribackend.dto.CollectivityOverallStatisticsDTO;
import org.hei.federationagribackend.repository.CollectivityOverallStatisticsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CollectivityOverallStatisticsService {

    private final CollectivityOverallStatisticsRepository repository;

    public CollectivityOverallStatisticsService(CollectivityOverallStatisticsRepository repository) {
        this.repository = repository;
    }

    public List<CollectivityOverallStatisticsDTO> getOverallStatistics(LocalDate from, LocalDate to) throws Exception {

        // 1. Récupérer toutes les collectivités
        List<Map<String, Object>> collectivities = repository.getAllCollectivities();

        // 2. Compter les nouveaux membres par collectivité
        Map<String, Integer> newMembersCount = repository.countNewMembersByPeriod(from, to);

        // 3. Compter le nombre total de membres par collectivité
        Map<String, Integer> totalMembersCount = repository.countTotalMembersByCollectivity();

        // 4. Récupérer le montant total des cotisations actives par collectivité
        Map<String, Double> activeFeesByCollectivity = repository.getTotalActiveFeesByCollectivity();

        // 5. Récupérer les montants payés par membre par collectivité
        Map<String, Map<String, Double>> paidByMember = repository.getTotalPaidByMemberByCollectivity(from, to);

        // 6. Construire la réponse
        List<CollectivityOverallStatisticsDTO> result = new ArrayList<>();

        for (Map<String, Object> collectivity : collectivities) {
            String collectivityId = (String) collectivity.get("id");

            // Informations de la collectivité
            CollectivityInformationDTO info = new CollectivityInformationDTO();
            info.setId(collectivityId);
            info.setName((String) collectivity.get("name"));
            info.setNumber((String) collectivity.get("number"));

            // Nombre de nouveaux membres
            Integer newMembers = newMembersCount.getOrDefault(collectivityId, 0);

            // Calcul du pourcentage
            Double totalFees = activeFeesByCollectivity.get(collectivityId);
            Map<String, Double> memberPayments = paidByMember.get(collectivityId);

            Double percentage;
            if (totalFees == null && (memberPayments == null || memberPayments.isEmpty())) {
                percentage = 100.0;  // Pas de cotisation, tous sont à jour
            } else if (totalFees == null || totalFees == 0.0) {
                percentage = 100.0;
            } else if (memberPayments == null || memberPayments.isEmpty()) {
                percentage = 0.0;
            } else {
                percentage = repository.calculateUpToDatePercentage(collectivityId, totalFees, memberPayments);
            }

            CollectivityOverallStatisticsDTO dto = new CollectivityOverallStatisticsDTO();
            dto.setCollectivityInformation(info);
            dto.setNewMembersNumber(newMembers);
            dto.setOverallMemberCurrentDuePercentage(percentage);

            result.add(dto);
        }

        return result;
    }
}