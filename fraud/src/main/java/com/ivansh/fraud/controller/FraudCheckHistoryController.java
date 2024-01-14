package com.ivansh.fraud.controller;

import com.ivansh.fraud.dto.FraudCheckResponse;
import com.ivansh.fraud.service.FraudCheckHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/fraud-check")
@RequiredArgsConstructor
@Slf4j
public class FraudCheckHistoryController {

    private final FraudCheckHistoryService fraudCheckHistoryService;

    @GetMapping(path = "{customerId}")
    public FraudCheckResponse isFraudster(@PathVariable("customerId") Long customerId) {
       Boolean isFraudster = fraudCheckHistoryService.isFraudster(customerId);
       log.info("fraud check history for customer {}", customerId);
       return new FraudCheckResponse(isFraudster);
    }
}
