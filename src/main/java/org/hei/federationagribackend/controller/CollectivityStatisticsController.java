package org.hei.federationagribackend.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.hei.federationagribackend.dto.CollectivityLocalStatisticsDTO;
import org.hei.federationagribackend.security.ApiKeyValidator;
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
    private final ApiKeyValidator apiKeyValidator;
    public CollectivityStatisticsController(CollectivityStatisticsService statisticsService, ApiKeyValidator apiKeyValidator) {
        this.statisticsService = statisticsService;
        this.apiKeyValidator = apiKeyValidator;
    }

    @GetMapping("/{id}/statistics")
    public ResponseEntity<?> getCollectivityStatistics(
            @PathVariable("id") String collectivityId,
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


            boolean exists = statisticsService.collectivityExists(collectivityId);
            if (!exists) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Collectivity not found: " + collectivityId);
            }

            List<CollectivityLocalStatisticsDTO> statistics =
                    statisticsService.getCollectivityLocalStatistics(collectivityId, from, to);
            return ResponseEntity.ok(statistics);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }
}