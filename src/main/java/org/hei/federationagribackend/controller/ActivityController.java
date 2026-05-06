package org.hei.federationagribackend.controller;

import org.hei.federationagribackend.dto.CreateCollectivityActivityDTO;
import org.hei.federationagribackend.service.ActivityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collectivities/{id}/activities")
public class ActivityController {
    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PostMapping
    public ResponseEntity<Void> createActivities(
            @PathVariable String id,
            @RequestBody List<CreateCollectivityActivityDTO> activities) {

        activityService.createAll(id, activities);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
