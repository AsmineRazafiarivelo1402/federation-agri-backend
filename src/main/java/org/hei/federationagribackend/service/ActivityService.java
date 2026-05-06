package org.hei.federationagribackend.service;

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

    public void createAll(String collectivityId, List<CreateCollectivityActivityDTO> dtos) {
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

            repository.save(activity);

            if (dto.getMemberOccupationConcerned() != null) {
                for (String occ : dto.getMemberOccupationConcerned()) {
                    repository.saveOccupation(activity.getId(), occ);
                }
            }
        }
    }
}
