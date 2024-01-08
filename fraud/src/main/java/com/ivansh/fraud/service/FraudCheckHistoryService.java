package com.ivansh.fraud.service;

import com.ivansh.fraud.dal.entity.FraudCheckHistory;
import com.ivansh.fraud.dal.repository.FraudCheckHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FraudCheckHistoryService {

    private final FraudCheckHistoryRepository fraudCheckHistoryRepository;

    public Boolean isFraudster(Long customerId) {
        FraudCheckHistory checkHistory = (FraudCheckHistory.builder()
                .isFraudster(false)
                .createdAt(LocalDateTime.now())
                .customerId(customerId)
                .build());
        fraudCheckHistoryRepository.save(checkHistory);
        return false;
    }
}
