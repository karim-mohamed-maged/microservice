package com.karim.service.impl;

import com.karim.model.FraudCheckHistory;
import com.karim.repostiory.FraudCheckHistoryRepostiory;
import com.karim.service.FraudCheckHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class FraudCheckHistoryServiceImpl implements FraudCheckHistoryService {

    private final FraudCheckHistoryRepostiory fraudCheckHistoryRepostiory;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public Boolean isFraudulentCustomer(Long customerId) {
        FraudCheckHistory fraudCheckHistory = FraudCheckHistory
                .builder()
                .customerId(customerId)
                .isFraudster(false)
                .createdAt(Instant.now())
                .build();
        fraudCheckHistory = fraudCheckHistoryRepostiory.save(fraudCheckHistory);
        rabbitTemplate.convertAndSend("my-exchange" , "my-routing" , "welcome my message");
        System.out.println("done");
        return fraudCheckHistory.getIsFraudster();
    }
}
