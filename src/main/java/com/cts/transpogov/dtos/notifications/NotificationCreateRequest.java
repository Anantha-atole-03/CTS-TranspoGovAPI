package com.cts.transpogov.dtos.notifications;

import com.cts.transpogov.enums.NotificationCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
//@Data
public class NotificationCreateRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    private String entityId;

    @NotBlank(message = "Message cannot be empty")
    private String message;

    @NotNull(message = "Category is required")
    private NotificationCategory category;

    // Getters & Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getEntityId() { return entityId; }
    public void setEntityId(String entityId) { this.entityId = entityId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public NotificationCategory getCategory() { return category; }
    public void setCategory(NotificationCategory category) { this.category = category; }
}
