package com.cts.transpogov.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cts.transpogov.dtos.notifications.NotificationCreateRequest;
import com.cts.transpogov.dtos.notifications.NotificationResponse;
import com.cts.transpogov.enums.NotificationStatus;
import com.cts.transpogov.models.Notification;
import com.cts.transpogov.models.User;
import com.cts.transpogov.repositories.INotificationRepository;
import com.cts.transpogov.repositories.IUserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class NotificationService implements INotificationService {

	private final INotificationRepository notificationRepository;
	private final IUserRepository userRepository;
	private final ModelMapper modelMapper;
	private final JavaMailSender mailSender;

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
	 * Description: Creates and persists a new notification for a user and triggers
	 * an email alert. It validates the user existence, builds the notification
	 * entity with an UNREAD status, saves it to the database, and dispatches a
	 * plain-text email before returning the NotificationResponse.
	 */

	@Override
	public NotificationResponse pushNotification(NotificationCreateRequest request) {

		log.info("Pushing notification for userId={} with entityId={}", request.getUserId(), request.getEntityId());

		User user = userRepository.findById(request.getUserId())
				.orElseThrow(() -> new RuntimeException("User not found"));

		Notification notification = Notification.builder().user(user).entityId(request.getEntityId())
				.message(request.getMessage()).category(request.getCategory()).status(NotificationStatus.UNREAD)
				.build();
		Notification savedNotification = notificationRepository.save(notification);
		// Trigger Email Sending
		sendEmail(user.getEmail(), "Update from TranspoGov", request.getMessage());
		return modelMapper.map(savedNotification, NotificationResponse.class);
	}

	/*
	 * Description: Internal helper method that handles the dispatch of email
	 * messages using JavaMailSender. It is wrapped in a try-catch block to ensure
	 * that email delivery failures do not roll back the primary database
	 * transaction for the notification record.
	 */
//	private void sendEmail(String to, String subject, String body) {
//		try {
//			SimpleMailMessage mailMessage = new SimpleMailMessage();
//			mailMessage.setTo(to);
//			mailMessage.setSubject(subject);
//			mailMessage.setText(body);
//			// mailMessage.setFrom("your-app@example.com"); // Optional: specify sender
//
//			mailSender.send(mailMessage);
//			log.info("Email sent successfully to {}", to);
//		} catch (Exception e) {
//			log.error("Failed to send email to {}: {}", to, e.getMessage());
//			// We usually don't throw an exception here so the DB transaction still
//			// completes
//		}
//
//	}
	/*
	 * Description: Internal helper method that constructs and sends an HTML-formatted 
	 * email. It uses MimeMessage and MimeMessageHelper to allow for rich text, 
	 * inline styling, and improved user experience. Errors are caught to prevent 
	 * interrupting the main notification flow.
	 */
	private void sendEmail(String to, String subject, String messageContent) {
		try {
			var mimeMessage = mailSender.createMimeMessage();
			// Use 'true' for multipart to support HTML
			var helper = new org.springframework.mail.javamail.MimeMessageHelper(mimeMessage, true, "UTF-8");

			// HTML Example: Professional Notification Template
			String htmlTemplate = 
				"<html>" +
				"<body style='font-family: Arial, sans-serif; line-height: 1.6; color: #333;'>" +
				"    <div style='max-width: 600px; margin: 20px auto; border: 1px solid #ddd; border-radius: 8px; overflow: hidden;'>" +
				"        <div style='background-color: #004a99; color: white; padding: 20px; text-align: center;'>" +
				"            <h1 style='margin: 0;'>TranspoGov Portal</h1>" +
				"        </div>" +
				"        <div style='padding: 20px;'>" +
				"            <p>Dear User,</p>" +
				"            <p>You have received a new update regarding your transport governance services:</p>" +
				"            <div style='background-color: #f9f9f9; border-left: 4px solid #004a99; padding: 15px; margin: 20px 0;'>" +
				"                <strong>Update:</strong> " + messageContent + "" +
				"            </div>" +
				"            <p>To view full details, please log in to your dashboard.</p>" +
				"            <a href='#' style='display: inline-block; padding: 10px 20px; background-color: #28a745; color: white; text-decoration: none; border-radius: 5px;'>View Dashboard</a>" +
				"        </div>" +
				"        <div style='background-color: #f4f4f4; color: #777; padding: 10px; text-align: center; font-size: 12px;'>" +
				"            &copy; 2026 TranspoGov. This is an automated notification." +
				"        </div>" +
				"    </div>" +
				"</body>" +
				"</html>";

			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(htmlTemplate, true); // The 'true' flag is vital for HTML rendering
			helper.setFrom("notifications@transpogov.com");

			mailSender.send(mimeMessage);
			log.info("HTML email successfully sent to {}", to);
			
		} catch (Exception e) {
			log.error("Failed to send HTML email to {}: {}", to, e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}
}
