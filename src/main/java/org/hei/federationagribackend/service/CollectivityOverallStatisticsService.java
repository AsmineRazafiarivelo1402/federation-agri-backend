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


        List<Map<String, Object>> collectivities = repository.getAllCollectivities();


        Map<String, Integer> newMembersCount = repository.countNewMembersByPeriod(from, to);

        Map<String, Double> theoreticalAmountByCollectivity = repository.getTotalTheoreticalAmountByCollectivityForPeriod(from, to);

        Map<String, Map<String, Double>> paidByMember = repository.getTotalPaidByMemberByCollectivity(from, to);

        List<CollectivityOverallStatisticsDTO> result = new ArrayList<>();

        for (Map<String, Object> collectivity : collectivities) {
            String collectivityId = (String) collectivity.get("id");

            CollectivityInformationDTO info = new CollectivityInformationDTO();
            info.setId(collectivityId);
            info.setName((String) collectivity.get("name"));
            info.setNumber((String) collectivity.get("number"));

            Integer newMembers = newMembersCount.getOrDefault(collectivityId, 0);

            Double theoreticalAmount = theoreticalAmountByCollectivity.get(collectivityId);
            Map<String, Double> memberPayments = paidByMember.get(collectivityId);

            Double percentage;

            if (theoreticalAmount == null || theoreticalAmount == 0.0) {
                percentage = 100.0;
            } else if (memberPayments == null || memberPayments.isEmpty()) {
                percentage = 0.0;
            } else {
                long upToDateCount = memberPayments.values().stream()
                        .filter(paid -> paid >= theoreticalAmount)
                        .count();
                percentage = (double) upToDateCount / memberPayments.size() * 100.0;
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