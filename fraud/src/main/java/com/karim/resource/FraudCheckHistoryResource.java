package com.karim.resource;

import com.karim.service.FraudCheckHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fraud")
@RequiredArgsConstructor
@Slf4j
public class FraudCheckHistoryResource {

    private final FraudCheckHistoryService fraudCheckHistoryService;

    @GetMapping("/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public Boolean isFraudulentCustomer(@PathVariable Long customerId){
        log.info("create fraud for new customer with Fraudster status false ");
        return fraudCheckHistoryService.isFraudulentCustomer(customerId);
    }
}
