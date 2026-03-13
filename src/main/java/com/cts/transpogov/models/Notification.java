package com.cts.transpogov.models;

import java.time.LocalDateTime;

import com.cts.transpogov.enums.NotificationCategory;
import com.cts.transpogov.enums.NotificationStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity @Table(name = "notifications")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Notification {
  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "notification_id", updatable = false, nullable = false)
  private Long notificationId;

  private Long userId;    
  private String entityId; 

  @Column(columnDefinition = "text")
  private String message;

  @Enumerated(EnumType.STRING)
  private NotificationCategory category;

  @Enumerated(EnumType.STRING)
  private NotificationStatus status;

  private LocalDateTime createdDate;
}