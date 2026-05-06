package org.hei.federationagribackend.controller;

import org.hei.federationagribackend.dto.CollectivityOverallStatisticsDTO;
import org.hei.federationagribackend.service.CollectivityOverallStatisticsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/collectivities")
public class CollectivityOverallStatisticsController {

    private final CollectivityOverallStatisticsService statisticsService;

    public CollectivityOverallStatisticsController(CollectivityOverallStatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/statistics")
    public ResponseEntity<?> getOverallStatistics(
            @RequestParam LocalDate from,
            @RequestParam LocalDate to
    ) {
        try {
            List<CollectivityOverallStatisticsDTO> statistics = statisticsService.getOverallStatistics(from, to);
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}