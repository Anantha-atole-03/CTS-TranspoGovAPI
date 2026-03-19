package com.cts.transpogov.controllers;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cts.transpogov.dtos.ApiResponse;
import com.cts.transpogov.dtos.notifications.NotificationCreateRequest;
import com.cts.transpogov.dtos.notifications.NotificationResponse;
import com.cts.transpogov.service.INotificationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/notifications")
public class NotificationController {

	private final INotificationService notificationService;

	public NotificationController(INotificationService notificationService) {
		this.notificationService = notificationService;
	}

	/*
	 * Method: GET Argument: userId (Long) Description: Fetches all notifications
	 * for the given user by calling getUserNotifications() service method
	 * Return:ResponseEntity<ApiResponse<List<NotificationResponse>>> type
	 */
	// GET /notifications?userId=123
	@GetMapping
	public ResponseEntity<ApiResponse<List<NotificationResponse>>> getUserNotifications(@RequestParam Long userId) {
		log.info("Fetching user notifications");
		List<NotificationResponse> notifications = notificationService.getUserNotifications(userId);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponse<>("Notification fetched", HttpStatus.OK.value(), notifications));
	}

	/*
	 * Method: PATCH Argument: notificationId (Long) Description: Marks a specific
	 * notification as read by calling markAsRead() service method
	 * Return:ResponseEntity<ApiResponse<NotificationResponse>> type
	 */
	// PATCH /notifications/{notificationId}
	@PatchMapping("/{notificationId}")
	public ResponseEntity<ApiResponse<NotificationResponse>> markAsRead(@PathVariable Long notificationId) {
		log.info("User Read the notifications");
		NotificationResponse response = notificationService.markAsRead(notificationId);
		return ResponseEntity.ok(new ApiResponse<>("Notification featch", HttpStatus.OK.value(), response));
	}

	/*
	 * Method: POST Argument: NotificationCreateRequest DTO Description: Accepts
	 * NotificationCreateRequest DTO and calls pushNotification() method to create a
	 * new notification Return:
	 * ResponseEntity<ApiResponse<NotificationResponse>>type
	 */

	// POST /notifications
	@PostMapping("/save")
	public ResponseEntity<ApiResponse<NotificationResponse>> pushNotification(
			@RequestBody NotificationCreateRequest request) {
		log.info("User got the New notifications");
		NotificationResponse response = notificationService.pushNotification(request);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ApiResponse<>("Notification pushed", HttpStatus.CREATED.value(), response));
	}
}
