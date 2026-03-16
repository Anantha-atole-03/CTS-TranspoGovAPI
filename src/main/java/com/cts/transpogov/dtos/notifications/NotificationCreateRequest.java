package com.cts.transpogov.dtos.notifications;

import com.cts.transpogov.enums.NotificationCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationCreateRequest {

    @NotNull(message = "Recipient user ID is required")
    private Long userId;

    private String entityId;

    @NotBlank(message = "Notification message cannot be empty")
    private String message;

    @NotNull(message = "Category is required")
    private NotificationCategory category;
}
