package com.ivansh.customer.service;

import com.ivansh.customer.FraudCheckResponse;
import com.ivansh.customer.dto.CustomerRegistrationRequest;
import com.ivansh.customer.dal.repository.CustomerRepository;
import com.ivansh.customer.dal.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;

    public void registerCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        Customer customer = Customer.builder()
                .firstname(customerRegistrationRequest.firstname())
                .lastname(customerRegistrationRequest.lastname())
                .email(customerRegistrationRequest.email())
                .build();

        customerRepository.saveAndFlush(customer);

        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
                "http://localhost:8081/api/v1/fraud-check/{customerId}",
                FraudCheckResponse.class,
                customer.getId()
        );

        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalArgumentException("Fraudster");
        }

        // todo check if email valid
        // todo check if email not taken
//        customerRepository.save(customer);
    }
}
