package com.ivansh.customer.service;

import com.ivansh.clients.fraud.FraudCheckResponse;
import com.ivansh.clients.fraud.FraudClient;
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
    private final FraudClient fraudClient;

    public void registerCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        Customer customer = Customer.builder()
                .firstname(customerRegistrationRequest.firstname())
                .lastname(customerRegistrationRequest.lastname())
                .email(customerRegistrationRequest.email())
                .build();

        customerRepository.saveAndFlush(customer);

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalArgumentException("Fraudster");
        }

        // todo check if email valid
        // todo check if email not taken
//        customerRepository.save(customer);
    }
}
