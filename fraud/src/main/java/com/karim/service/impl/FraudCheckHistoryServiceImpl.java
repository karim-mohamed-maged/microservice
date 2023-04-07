package com.karim.service.impl;

import com.karim.model.FraudCheckHistory;
import com.karim.repostiory.FraudCheckHistoryRepostiory;
import com.karim.service.FraudCheckHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class FraudCheckHistoryServiceImpl implements FraudCheckHistoryService {
    private final FraudCheckHistoryRepostiory fraudCheckHistoryRepostiory;


    @Override
    public Boolean isFraudulentCustomer(Long customerId) {
        FraudCheckHistory fraudCheckHistory = FraudCheckHistory
                .builder()
                .customerId(customerId)
                .isFraudster(false)
                .createdAt(Instant.now())
                .build();
        fraudCheckHistory = fraudCheckHistoryRepostiory.save(fraudCheckHistory);
        return fraudCheckHistory.getIsFraudster();
    }
}
