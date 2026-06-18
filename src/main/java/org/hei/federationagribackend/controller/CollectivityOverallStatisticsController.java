package org.hei.federationagribackend.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.hei.federationagribackend.dto.CollectivityOverallStatisticsDTO;
import org.hei.federationagribackend.security.ApiKeyValidator;
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
    private final ApiKeyValidator apiKeyValidator;
    public CollectivityOverallStatisticsController(CollectivityOverallStatisticsService statisticsService, ApiKeyValidator apiKeyValidator) {
        this.statisticsService = statisticsService;
        this.apiKeyValidator = apiKeyValidator;
    }

    @GetMapping("/statistics")
    public ResponseEntity<?> getOverallStatistics(
            @RequestParam LocalDate from,
            @RequestParam LocalDate to,
            HttpServletRequest request
    ) {
        apiKeyValidator.validate(request);
        try {

            if (from.isAfter(to)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("'from' date must be before or equal to 'to' date");
            }

            List<CollectivityOverallStatisticsDTO> statistics =
                    statisticsService.getOverallStatistics(from, to);
            return ResponseEntity.ok(statistics);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }
}