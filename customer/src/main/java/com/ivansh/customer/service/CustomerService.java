package com.ivansh.customer.service;

import com.ivansh.amqp.cofiguration.RabbitMQMessageProducer;
import com.ivansh.clients.fraud.FraudCheckResponse;
import com.ivansh.clients.fraud.FraudClient;
import com.ivansh.clients.notification.NotificationClient;
import com.ivansh.clients.notification.NotificationRequest;
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
    private final RabbitMQMessageProducer rabbitMQMessageProducer;

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

        NotificationRequest notificationRequest = new NotificationRequest(
                customer.getId(),
                customer.getEmail(),
                String.format("Hello %s!",
                        customer.getFirstname())
        );
        rabbitMQMessageProducer.publish(
                notificationRequest,
                "internal.exchange",
                "internal.notification.routing-key"
        );

        // todo check if email valid
        // todo check if email not taken
//        customerRepository.save(customer);
    }
}
