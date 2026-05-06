package org.hei.federationagribackend.controller;

import org.hei.federationagribackend.dto.CollectivityLocalStatisticsDTO;
import org.hei.federationagribackend.service.CollectivityStatisticsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/collectivites")
public class CollectivityStatisticsController {

    private final CollectivityStatisticsService statisticsService;

    public CollectivityStatisticsController(CollectivityStatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/{id}/statistics")
    public ResponseEntity<?> getCollectivityStatistics(
            @PathVariable("id") String collectivityId,
            @RequestParam LocalDate from,
            @RequestParam LocalDate to
    ) {
        try {
            List<CollectivityLocalStatisticsDTO> statistics = statisticsService.getCollectivityLocalStatistics(collectivityId, from, to);
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}