package org.hei.federationagribackend.controller;

import org.hei.federationagribackend.entity.CollectivityEntity;
import org.hei.federationagribackend.service.CollectivityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collectivities")
public class CollectivityController {

//    private final CollectivityService collectivityService;
//
//    public CollectivityController(CollectivityService collectivityService) {
//        this.collectivityService = collectivityService;
//    }
//
//    @PostMapping
//    public ResponseEntity<?> createCollectivities(@RequestBody List<CollectivityEntity> collectivities) {
//        try {
//            List<CollectivityEntity> createdCollectivities = collectivityService.createCollectivities(collectivities);
//            return ResponseEntity.status(HttpStatus.CREATED).body(createdCollectivities);
//        } catch (IllegalArgumentException e) {
//            String message = e.getMessage();
//            if (message.contains("not found")) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
//            }
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
//        }
//    }
}