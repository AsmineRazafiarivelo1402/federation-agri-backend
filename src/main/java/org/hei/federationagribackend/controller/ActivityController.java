package org.hei.federationagribackend.controller;

import org.hei.federationagribackend.dto.CreateCollectivityActivityDTO;
import org.hei.federationagribackend.exception.ErrorResponse;
import org.hei.federationagribackend.service.ActivityService;
import org.hei.federationagribackend.service.CollectivityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collectivities/{id}/activities")
public class ActivityController {

    private final ActivityService activityService;
    private final CollectivityService collectivityService;

    public ActivityController(ActivityService activityService, CollectivityService collectivityService) {
        this.activityService = activityService;
        this.collectivityService = collectivityService;
    }

    @PostMapping
    public ResponseEntity<Object> createActivities(
            @PathVariable String id,
            @RequestBody List<CreateCollectivityActivityDTO> activities) {

        if (!collectivityService.existsById(id)) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Collectivities not found"));
        }

        for (CreateCollectivityActivityDTO activity : activities) {
            if (activity.getRecurrenceRule() != null && activity.getExecutiveDate() != null) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse("Both recurrence rule and executive date provided"));
            }
        }

        List<CreateCollectivityActivityDTO> created = activityService.createAll(id, activities);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<Object> getActivities(@PathVariable String id) {

        if (!collectivityService.existsById(id)) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Collectivity not found"));
        }

        List<CreateCollectivityActivityDTO> activities = activityService.getActivitiesByCollectivity(id);
        return ResponseEntity.status(HttpStatus.OK).body(activities);
    }
}
