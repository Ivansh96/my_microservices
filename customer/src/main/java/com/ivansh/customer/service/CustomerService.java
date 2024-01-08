package com.ivansh.customer.service;

import com.ivansh.customer.dto.CustomerRegistrationRequest;
import com.ivansh.customer.dal.repository.CustomerRepository;
import com.ivansh.customer.dal.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public void registerCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        Customer customer = Customer.builder()
                .firstname(customerRegistrationRequest.firstname())
                .lastname(customerRegistrationRequest.lastname())
                .email(customerRegistrationRequest.email())
                .build();

        // todo check if email valid
        // todo check if email not taken
        customerRepository.save(customer);
    }
}
