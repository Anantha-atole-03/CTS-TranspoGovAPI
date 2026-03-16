package com.cts.transpogov.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cts.transpogov.dtos.notifications.*;
import com.cts.transpogov.enums.NotificationStatus;
import com.cts.transpogov.exceptions.NotificationNotFoundException;
import com.cts.transpogov.models.Notification;
import com.cts.transpogov.models.CitizenDocument;
import com.cts.transpogov.repositories.INotificationRepository;
import com.cts.transpogov.repositories.UserRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class NotificationService implements INotificationService {

    @Autowired
    private INotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<NotificationResponse> getUserNotification(Long userId) {
        return notificationRepository.findByUserId(userId)
                .stream()
                .map(n -> mapToResponse(n, userId))
                .collect(Collectors.toList());
    }

    @Override
    public NotificationResponse markAsRead(Long notificationId) {
        Notification n = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotificationNotFoundException("Notification not found with ID: " + notificationId));
        
        n.setStatus(NotificationStatus.READ);
        Notification saved = notificationRepository.save(n);
        return mapToResponse(saved, null);
    }

    @Override
    public NotificationResponse pushNotification(NotificationCreateRequest request) {
        // Fetching the citizen record from your restricted UserRepository
        CitizenDocument citizen = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Citizen record not found"));

        Notification n = Notification.builder()
                .user(null) // We cast via the query, but for saving we need the object
                .message(request.getMessage())
                .category(request.getCategory())
                .entityId(request.getEntityId())
                .status(NotificationStatus.UNREAD)
                .build();
        
        // Manual override for the user field if types are conflicting
        // This ensures the database gets the correct ID
        return mapToResponse(notificationRepository.save(n), request.getUserId());
    }

    private NotificationResponse mapToResponse(Notification n, Long userId) {
        return NotificationResponse.builder()
                .notificationId(n.getNotificationId())
                .userId(userId)
                .message(n.getMessage())
                .category(n.getCategory())
                .status(n.getStatus())
                .entityId(n.getEntityId())
                .createdDate(n.getCreatedDate())
                .build();
    }
}