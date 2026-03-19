package com.cts.transpogov.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.cts.transpogov.dtos.notifications.NotificationCreateRequest;
import com.cts.transpogov.dtos.notifications.NotificationResponse;
import com.cts.transpogov.enums.NotificationStatus;
import com.cts.transpogov.models.Notification;
import com.cts.transpogov.models.User;
import com.cts.transpogov.repositories.INotificationRepository;
import com.cts.transpogov.repositories.IUserRepository;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class NotificationService implements INotificationService {

	private final INotificationRepository notificationRepository;
	private final IUserRepository userRepository;
	private final ModelMapper modelMapper;

	/*
	 * Description:Fetches all notifications belonging to the specified user. It
	 * retrieves the user from the database, fetches all associated notifications,
	 * and maps them into NotificationResponse DTOs.)
	 */

	@Override
	public List<NotificationResponse> getUserNotifications(Long userId) {
		log.info("Fetching user notifications for userId={}", userId);
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		return notificationRepository.findByUser(user).stream()
				.map((notification) -> modelMapper.map(notification, NotificationResponse.class))
				.collect(Collectors.toList());
	}

	/*
	 * Description:Marks a specific notification as READ. It looks up the
	 * notification by ID, updates its status, save the change, and return the
	 * updated notification as a NotigicationResponse
	 */

	@Override
	public NotificationResponse markAsRead(Long notificationId) {
		log.info("Marking notification {} as read", notificationId);
		Notification notification = notificationRepository.findById(notificationId)
				.orElseThrow(() -> new RuntimeException("Notification not found"));
		notification.setStatus(NotificationStatus.READ);
		return modelMapper.map(notificationRepository.save(notification), NotificationResponse.class);
	}

	/*
	 * Description: Creates and sends a new notification for a user. It validates
	 * the user, builds the notification entity using the request data, saves it,
	 * and returns the newly created notification as a NotificationResponse.
	 */

	@Override
	public NotificationResponse pushNotification(NotificationCreateRequest request) {

		log.info("Pushing notification for userId={} with entityId={}", request.getUserId(), request.getEntityId());

		User user = userRepository.findById(request.getUserId())
				.orElseThrow(() -> new RuntimeException("User not found"));

		Notification notification = Notification.builder().user(user).entityId(request.getEntityId())
				.message(request.getMessage()).category(request.getCategory()).status(NotificationStatus.UNREAD)
				.build();

		return modelMapper.map(notificationRepository.save(notification), NotificationResponse.class);
	}

}
