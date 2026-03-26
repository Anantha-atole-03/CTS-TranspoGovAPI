package com.cts.transpogov.dtos.notifications;

import java.time.LocalDateTime;
import com.cts.transpogov.enums.NotificationCategory;
import com.cts.transpogov.enums.NotificationStatus;

public class NotificationResponse {
    private Long notificationId;
    private String message;
    private NotificationCategory category;
    private NotificationStatus status;
    private LocalDateTime createdDate;

    // Getters & Setters
    public Long getNotificationId() { return notificationId; }
    public void setNotificationId(Long notificationId) { this.notificationId = notificationId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

   
    public NotificationCategory getCategory() { return category; }
    public void setCategory(NotificationCategory category) { this.category = category; }

    public NotificationStatus getStatus() { return status; }
    public void setStatus(NotificationStatus status) { this.status = status; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
}
