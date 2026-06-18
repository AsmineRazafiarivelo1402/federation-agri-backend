package org.hei.federationagribackend.service;

import org.hei.federationagribackend.Interface.FinancialAccount;
import org.hei.federationagribackend.entity.CollectivityTransactionEntity;
import org.hei.federationagribackend.repository.CollectivityTransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CollectivityTransactionService {

    private final CollectivityTransactionRepository repository;

    public CollectivityTransactionService(CollectivityTransactionRepository repository) {
        this.repository = repository;
    }

    public List<CollectivityTransactionEntity> getTransactions(
            String collectivityId,
            LocalDate from,
            LocalDate to
    ) throws Exception {


        if (from == null || to == null) {
            throw new IllegalArgumentException("from and to dates are required");
        }

        if (from.isAfter(to)) {
            throw new IllegalArgumentException("'from' date must be before 'to' date");
        }

        return repository.findByCollectivityAndDates(collectivityId, from, to);
    }
    public List<FinancialAccount> getFinancialAccounts(String id, LocalDate at) {
        if (at == null) {
            throw new IllegalArgumentException("Date 'at' is required");
        }

        try {
            return repository.findAccountsByCollectivityAndDate(id, at);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}