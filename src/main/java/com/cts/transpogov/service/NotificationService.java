package com.cts.transpogov.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cts.transpogov.dtos.notifications.NotificationCreateRequest;
import com.cts.transpogov.dtos.notifications.NotificationResponse;
import com.cts.transpogov.enums.NotificationCategory;
import com.cts.transpogov.enums.NotificationScope;
import com.cts.transpogov.enums.NotificationStatus;

import com.cts.transpogov.models.Citizen;
import com.cts.transpogov.models.Notification;
import com.cts.transpogov.models.User;
import com.cts.transpogov.repositories.ICitizenRepository;
import com.cts.transpogov.repositories.INotificationRepository;
import com.cts.transpogov.repositories.IUserRepository;
import com.cts.transpogov.utils.MailTemplates;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class NotificationService implements INotificationService {

	private final ICitizenRepository citizenRepository;

	private final INotificationRepository notificationRepository;
	private final IUserRepository userRepository;
	private final ModelMapper modelMapper;
	private final JavaMailSender mailSender;

	/**
	 * Retrieves all notifications belonging to a specific user. Validates that the
	 * user exists, then fetches notifications and maps them into
	 * NotificationResponse DTOs.
	 *
	 * @param userId ID of the user whose notifications are needed
	 * @return List of notification responses for the user
	 */

	@Override
	public List<NotificationResponse> getUserNotifications(Long userId) {

		userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		return notificationRepository.fetchUserNotifications(userId).stream()
				.map(notification -> modelMapper.map(notification, NotificationResponse.class))
				.collect(Collectors.toList());

	}

	/**
	 * Retrieves all notifications belonging to a specific citizen. Validates that
	 * the citizen exists, fetches notifications, and maps them into
	 * NotificationResponse DTOs.
	 *
	 * @param citizenId ID of the citizen
	 * @return List of notification responses for the citizen
	 */

	@Override
	public List<NotificationResponse> getCitizenNotifications(Long citizenId) {

		citizenRepository.findById(citizenId).orElseThrow(() -> new RuntimeException("Citizen not found"));

		return notificationRepository.fetchCitizenNotifications(citizenId).stream()
				.map(notification -> modelMapper.map(notification, NotificationResponse.class))
				.collect(Collectors.toList());

	}

	/**
	 * Marks a specific notification as READ.
	 *
	 * @param notificationId ID of the notification to update
	 * @return Updated notification as NotificationResponse
	 */

	@Override
	public NotificationResponse markAsRead(Long notificationId) {

		Notification notification = notificationRepository.findById(notificationId)
				.orElseThrow(() -> new RuntimeException("Notification not found"));

		notification.setStatus(NotificationStatus.READ);

		return modelMapper.map(notificationRepository.save(notification), NotificationResponse.class);
	}

	/**
	 * Sends a manual notification from a user to another user based on email. Saves
	 * the notification and triggers an email with the provided message.
	 *
	 * @param request contains message, sender userId, and receiver email
	 * @return Saved notification mapped to NotificationResponse
	 */

	@Override
	public NotificationResponse pushNotification(NotificationCreateRequest request) {

		log.info("Pushing notification manually for email={}", request.getEmail());

		User user = userRepository.findById(request.getUserId())
				.orElseThrow(() -> new RuntimeException("User not found with Id"));
		User user2 = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new RuntimeException("User not found with email"));

		Notification notification = Notification.builder().user(user).entityId(user2.getUserId())
				.message(request.getMessage()).category(request.getCategory()).status(NotificationStatus.UNREAD)
				.build();

		notification.setUser(user);
		notification.setScope(NotificationScope.USER);

		Notification savedNotification = notificationRepository.save(notification);

		String emailContent = MailTemplates.getGeneralNotificationTemplate(user.getName(), request.getMessage());

		sendEmail(user2.getEmail(), "TranspoGov Notification", emailContent);

		return modelMapper.map(savedNotification, NotificationResponse.class);
	}

	/**
	 * Broadcasts a system-generated notification to all citizens.
	 *
	 * @param message Notification message to broadcast
	 */

	public void sendAllCitizenNotification(String message) {

		log.info("Broadcasting notification to all citizens");

		Notification notification = Notification.builder().message(message).scope(NotificationScope.ALL_CITIZENS)
				.category(NotificationCategory.PROGRAM).status(NotificationStatus.UNREAD).build();

		notificationRepository.save(notification);
	}

	/**
	 * Broadcasts a system-generated notification to all registered system users.
	 *
	 * @param message Notification message to broadcast
	 */

	public void sendAllUserNotification(String message) {

		log.info("Broadcasting notification to all users");

		Notification notification = Notification.builder().message(message).scope(NotificationScope.ALL_USERS)
				.category(NotificationCategory.ROUTE).status(NotificationStatus.UNREAD).build();

		notificationRepository.save(notification);
	}

	/**
	 * Sends a global notification intended for the entire system, including users,
	 * citizens, and admins.
	 *
	 * @param message Notification message
	 */

	public void sendGlobalNotification(String message) {

		log.info("Broadcasting notification to entire system");

		Notification notification = Notification.builder().message(message).scope(NotificationScope.GLOBAL)
				.category(NotificationCategory.PROGRAM).status(NotificationStatus.UNREAD).build();

		notificationRepository.save(notification);
	}

	/**
	 * Sends an OTP notification to a citizen based on email. Saves the notification
	 * and emails the OTP.
	 *
	 * @param email Citizen’s email address
	 * @param otp   One-Time Password to be sent
	 */

	public void sendOtpNotification(String email, String otp) {

		log.info("System generated OTP notification for email={}", email);

		Citizen user = citizenRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Email not found"));

		Notification notification = Notification.builder().citizen(user).message("OTP:" + otp)
				.scope(NotificationScope.CITIZEN).category(NotificationCategory.OTP).status(NotificationStatus.UNREAD)
				.build();

		notificationRepository.save(notification);

		String emailContent = MailTemplates.getOtpTemplate(user.getName(), otp);

		sendEmail(user.getEmail(), "OTP Verification", emailContent);
	}

	/**
	 * Sends a ticket booking notification to a citizen. Includes route information
	 * and ticket ID.
	 *
	 * @param email    Citizen’s email address
	 * @param route    Route details
	 * @param ticketId Ticket identifier
	 */

	public void sendTicketNotification(String email, String route, Long ticketId) {

		log.info("System generated Ticket notification for email={}", email);

		Citizen user = citizenRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Email not found"));

		Notification notification = Notification.builder().entityId(user.getCitizenId()).message(route)
				.category(NotificationCategory.TICKET).status(NotificationStatus.UNREAD).build();

		notificationRepository.save(notification);

		String emailContent = MailTemplates.getTicketBookedTemplate(user.getName(), route, ticketId);

		sendEmail(user.getEmail(), "Ticket Booked", emailContent);
	}

	/**
	 * Sends a new program assignment notification to a system user.
	 *
	 * @param email       User’s email address
	 * @param title       Title of the program
	 * @param description Program description
	 */

	public void sendProgramNotification(String email, String title, String description) {

		log.info("System generated Program notification for email={}", email);

		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

		Notification notification = Notification.builder().user(user).message(title)
				.category(NotificationCategory.PROGRAM).status(NotificationStatus.UNREAD).build();

		notificationRepository.save(notification);

		String emailContent = MailTemplates.getNewProgramTemplate(user.getName(), title, description);

		sendEmail(user.getEmail(), "Program Assigned", emailContent);
	}

	/**
	 * Sends a route update notification to a system user.
	 *
	 * @param email User's email
	 * @param route Updated route details
	 */

	public void sendRouteUpdateNotification(String email, String route) {

		log.info("System generated Route update for email={}", email);

		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

		Notification notification = Notification.builder().user(user).message(route).scope(NotificationScope.USER)
				.category(NotificationCategory.ROUTE).status(NotificationStatus.UNREAD).build();

		notificationRepository.save(notification);

		String emailContent = MailTemplates.getRouteUpdateTemplate(user.getName(), route);

		sendEmail(user.getEmail(), "Route Updated", emailContent);
	}

	/**
	 * Sends a compliance-related alert to a user.
	 *
	 * @param email  User’s email address
	 * @param entity Compliance alert content
	 */

	public void sendComplianceNotification(String email, String entity) {

		log.info("System generated Compliance alert for email={}", email);

		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

		Notification notification = Notification.builder().user(user).message(entity)
				.category(NotificationCategory.COMPLIANCE).status(NotificationStatus.UNREAD).build();

		notificationRepository.save(notification);

		String emailContent = MailTemplates.getComplianceAlertTemplate(user.getName(), entity);

		sendEmail(user.getEmail(), "Compliance Alert", emailContent);
	}

	/**
	 * Internal reusable method for sending formatted HTML emails.
	 *
	 * @param to           Recipient email address
	 * @param subject      Email subject
	 * @param htmlTemplate HTML content of the email
	 */

	private void sendEmail(String to, String subject, String htmlTemplate) {

		try {

			var mimeMessage = mailSender.createMimeMessage();

			var helper = new org.springframework.mail.javamail.MimeMessageHelper(mimeMessage, true, "UTF-8");

			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(htmlTemplate, true);
			helper.setFrom("notifications@transpogov.com");

			mailSender.send(mimeMessage);

			log.info("Email successfully sent to {}", to);

		} catch (Exception e) {

			log.error("Failed to send email to {}: {}", to, e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Test method to trigger all notification flows. Useful for verifying email
	 * templates and system notifications.
	 */

	@Transactional
	@Override
	public void testAll() {
		// OTP Notification for Citizen
		sendOtpNotification("atoleanantha03@gmail.com", "4567");

		// Ticket Booking Notification for Citizen
		sendTicketNotification("atoleanantha03@gmail.com", "Bus Route 101", 1L);

		// Program Notification for User
		sendProgramNotification("apreamey2463@gmail.com", "Free Travel Program", "Eligible for free travel in Metro");

		// Route Update Notification for User
		sendRouteUpdateNotification("apreamey2463@gmail.com", "Route 55 Updated");

		// Compliance Alert Notification for User
		sendComplianceNotification("apreamey2463@gmail.com", "Ticket Fraud Alert");

		// Broadcast Notification to ALL Citizens
		sendAllCitizenNotification("New Transport Scheme Available");

		// Broadcast Notification to ALL Users
		sendAllUserNotification("Internal System Update");

		// Global Notification for Entire System
		sendGlobalNotification("System Maintenance Tonight");
	}
}