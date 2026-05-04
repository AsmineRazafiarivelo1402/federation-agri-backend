package org.hei.federationagribackend.controller;

import org.hei.federationagribackend.dto.AssignCollectivityIdentityDTO;
import org.hei.federationagribackend.dto.CreateCollectivityDTO;
import org.hei.federationagribackend.entity.CollectivityEntity;
import org.hei.federationagribackend.service.CollectivityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collectivities")
public class CollectivityController {

    private final CollectivityService service;

    public CollectivityController(CollectivityService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody List<CreateCollectivityDTO> dtos) {

        try {
            List<CollectivityEntity> result = service.create(dtos);
            return ResponseEntity.status(201).body(result);

        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    @PutMapping("/{id}/informations")
    public ResponseEntity<?> assignIdentity(
            @PathVariable String id,
            @RequestBody AssignCollectivityIdentityDTO dto
    ) {

        try {
            CollectivityEntity result = service.assignIdentity(id, dto);
            return ResponseEntity.ok(result);

        } catch (RuntimeException e) {

            if (e.getMessage().contains("not found")) {
                return ResponseEntity.status(404).body(e.getMessage());
            }

            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getCollectivity(@PathVariable String id) {
        try {
            return ResponseEntity.ok(service.getCollectivityById(id));
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Collectivity not found");
        }
    }

}