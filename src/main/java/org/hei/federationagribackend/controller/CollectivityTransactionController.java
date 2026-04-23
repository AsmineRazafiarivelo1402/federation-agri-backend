package org.hei.federationagribackend.controller;

import org.hei.federationagribackend.entity.CollectivityTransactionEntity;
import org.hei.federationagribackend.service.CollectivityTransactionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/collectivities")
public class CollectivityTransactionController {

    private final CollectivityTransactionService service;

    public CollectivityTransactionController(CollectivityTransactionService service) {
        this.service = service;
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<?> getTransactions(
            @PathVariable("id") String collectivityId,

            @RequestParam("from")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate from,

            @RequestParam("to")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate to
    ) {
        try {
            List<CollectivityTransactionEntity> transactions =
                    service.getTransactions(collectivityId, from, to);

            return ResponseEntity.ok(transactions);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Internal server error");
        }
    }
    @GetMapping("/{id}/financialAccounts")
    public ResponseEntity<?> getFinancialAccounts(
            @PathVariable String id,
            @RequestParam("at") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate at
    ) {
        try {
            return ResponseEntity.ok(service.getFinancialAccounts(id, at));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Collectivity not found");
        }
    }
}