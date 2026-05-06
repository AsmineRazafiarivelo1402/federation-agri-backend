package org.hei.federationagribackend.service;

import org.hei.federationagribackend.dto.MonthlyRecurrenceRuleDTO;
import org.hei.federationagribackend.entity.Activity;
import org.hei.federationagribackend.repository.ActivityRepository;
import org.hei.federationagribackend.dto.CreateCollectivityActivityDTO;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;

@Service
public class ActivityService {
    private final ActivityRepository repository;

    public ActivityService(ActivityRepository repository) {
        this.repository = repository;
    }

    public List<CreateCollectivityActivityDTO> getActivitiesByCollectivity(String collectivityId) {
        List<Activity> entities = repository.findAllByCollectivityId(collectivityId);

        return entities.stream().map(entity -> {
            CreateCollectivityActivityDTO dto = new CreateCollectivityActivityDTO();
            dto.setLabel(entity.getLabel());
            dto.setActivityType(entity.getActivityType());
            dto.setExecutiveDate(entity.getExecutiveDate());

            if (entity.getWeekOrdinal() != null) {
                MonthlyRecurrenceRuleDTO rule = new MonthlyRecurrenceRuleDTO();
                rule.setWeekOrdinal(entity.getWeekOrdinal());
                rule.setDayOfWeek(entity.getDayOfWeek());
                dto.setRecurrenceRule(rule);
            }

            List<String> occupations = repository.findOccupationsByActivityId(entity.getId());
            dto.setMemberOccupationConcerned(occupations);

            return dto;
        }).toList();
    }



    public List<CreateCollectivityActivityDTO> createAll(String collectivityId, List<CreateCollectivityActivityDTO> dtos) {
        for (CreateCollectivityActivityDTO dto : dtos) {
            Activity activity = new Activity();
            activity.setId(UUID.randomUUID().toString());
            activity.setCollectivityId(collectivityId);
            activity.setLabel(dto.getLabel());
            activity.setActivityType(dto.getActivityType());

            if (dto.getRecurrenceRule() != null) {
                activity.setWeekOrdinal(dto.getRecurrenceRule().getWeekOrdinal());
                activity.setDayOfWeek(dto.getRecurrenceRule().getDayOfWeek());
            } else {
                activity.setExecutiveDate(dto.getExecutiveDate());
            }

            repository.save(activity, dto.getMemberOccupationConcerned());

            if (dto.getMemberOccupationConcerned() != null) {
                for (String occ : dto.getMemberOccupationConcerned()) {
                    repository.saveOccupation(activity.getId(), occ);
                }
            }
        }

        // Retourne la liste reçue pour confirmer au client ce qui a été créé
        return dtos;
    }


}
