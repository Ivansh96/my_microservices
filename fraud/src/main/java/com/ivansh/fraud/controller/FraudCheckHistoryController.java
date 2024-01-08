package com.ivansh.fraud.controller;

import com.ivansh.fraud.dto.FraudCheckResponse;
import com.ivansh.fraud.service.FraudCheckHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/fraud-check")
@RequiredArgsConstructor
public class FraudCheckHistoryController {

    private final FraudCheckHistoryService fraudCheckHistoryService;

    @GetMapping(path = "{customerId}")
    public FraudCheckResponse isFraudster(@PathVariable("customerId") Long customerId) {
       Boolean isFraudster = fraudCheckHistoryService.isFraudster(customerId);
       return new FraudCheckResponse(isFraudster);
    }
}
