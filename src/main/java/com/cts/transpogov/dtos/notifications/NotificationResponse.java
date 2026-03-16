package com.cts.transpogov.dtos.notifications;

import java.time.LocalDateTime;
import com.cts.transpogov.enums.NotificationCategory;
import com.cts.transpogov.enums.NotificationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponse {
    private Long notificationId;
    private Long userId;
    private String userName; // Useful for the frontend to show who it's for
    private String entityId;
    private String message;
    private NotificationCategory category;
    private NotificationStatus status;
    private LocalDateTime createdDate;
}
