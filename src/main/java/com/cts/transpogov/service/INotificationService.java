package com.cts.transpogov.service;


import java.util.List;
import com.cts.transpogov.dtos.notifications.NotificationCreateRequest;
import com.cts.transpogov.dtos.notifications.NotificationResponse;

public interface INotificationService {
    List<NotificationResponse> getUserNotifications(Long userId);
    NotificationResponse markAsRead(Long notificationId);
    NotificationResponse pushNotification(NotificationCreateRequest request);
}