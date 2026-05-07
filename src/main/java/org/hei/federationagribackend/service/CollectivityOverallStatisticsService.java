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

        // 1. Récupération de toutes les données depuis le repository
        List<Map<String, Object>> collectivities = repository.getAllCollectivities();
        Map<String, Integer> newMembersCount = repository.countNewMembersByPeriod(from, to);
        Map<String, Double> theoreticalAmountByCollectivity = repository.getTotalTheoreticalAmountByCollectivityForPeriod(from, to);
        Map<String, Map<String, Double>> paidByMember = repository.getTotalPaidByMemberByCollectivity(from, to);

        // AJOUT BONUS 2 : Récupération des taux d'assiduité par collectivité
        Map<String, Double> assiduityPercentages = repository.getOverallAssiduityPercentageByCollectivity(from, to);

        List<CollectivityOverallStatisticsDTO> result = new ArrayList<>();

        // 2. Construction de la liste des DTOs
        for (Map<String, Object> collectivity : collectivities) {
            String collectivityId = (String) collectivity.get("id");

            // Information de base de la collectivité
            CollectivityInformationDTO info = new CollectivityInformationDTO();
            info.setId(collectivityId);
            info.setName((String) collectivity.get("name"));
            info.setNumber((String) collectivity.get("number"));

            // Nombre de nouveaux membres
            Integer newMembers = newMembersCount.getOrDefault(collectivityId, 0);

            // Calcul du pourcentage de membres à jour (Finances)
            Double theoreticalAmount = theoreticalAmountByCollectivity.get(collectivityId);
            Map<String, Double> memberPayments = paidByMember.get(collectivityId);

            Double financePercentage;
            if (theoreticalAmount == null || theoreticalAmount == 0.0) {
                financePercentage = 100.0;
            } else if (memberPayments == null || memberPayments.isEmpty()) {
                financePercentage = 0.0;
            } else {
                long upToDateCount = memberPayments.values().stream()
                        .filter(paid -> paid >= theoreticalAmount)
                        .count();
                financePercentage = (double) upToDateCount / memberPayments.size() * 100.0;
            }

            // 3. Création du DTO final
            CollectivityOverallStatisticsDTO dto = new CollectivityOverallStatisticsDTO();
            dto.setCollectivityInformation(info);
            dto.setNewMembersNumber(newMembers);
            dto.setOverallMemberCurrentDuePercentage(financePercentage);

            // AJOUT BONUS 2 : Injection du taux d'assiduité global
            // On utilise getOrDefault pour mettre 0.0 si aucune activité n'a eu lieu
            dto.setOverallMemberAssiduityPercentage(assiduityPercentages.getOrDefault(collectivityId, 0.0));

            result.add(dto);
        }

        return result;
    }
}