package com.ivansh.notification.service;

import com.ivansh.clients.notification.NotificationRequest;
import com.ivansh.notification.dal.entity.Notification;
import com.ivansh.notification.dal.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void sendNotification(NotificationRequest notificationRequest) {
        Notification notification = Notification.builder()
                .toCustomerId(notificationRequest.toCustomerId())
                .toCustomerEmail(notificationRequest.toCustomerEmail())
                .message(notificationRequest.message())
                .sender("Anonymous sender")
                .sentAt(LocalDateTime.now())
                .build();

        notificationRepository.save(notification);
    }
}
