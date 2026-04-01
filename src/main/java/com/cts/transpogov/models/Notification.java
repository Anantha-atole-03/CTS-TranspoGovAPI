package com.cts.transpogov.models;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.cts.transpogov.enums.NotificationCategory;
import com.cts.transpogov.enums.NotificationScope;
import com.cts.transpogov.enums.NotificationStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "notification_id", updatable = false, nullable = false)
	private Long notificationId;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = true)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "citizen_id", nullable = true)
	private Citizen citizen;

	private Long entityId;

	@NotBlank(message = "Notification message cannot be empty")
	@Column(columnDefinition = "text", nullable = false)
	private String message;

	@NotNull(message = "Category is reuired")
	@Enumerated(EnumType.STRING)
	private NotificationCategory category;

	@Enumerated(EnumType.STRING)
	private NotificationScope scope;

	@NotNull(message = " Status is required")
	@Enumerated(EnumType.STRING)
	private NotificationStatus status;

	@CreationTimestamp
	@Column(name = "created_date", updatable = false)
	private LocalDateTime createdDate;
}