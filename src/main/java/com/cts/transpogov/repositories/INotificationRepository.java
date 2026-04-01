package com.cts.transpogov.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cts.transpogov.models.Notification;
import com.cts.transpogov.models.User;

@Repository
public interface INotificationRepository extends JpaRepository<Notification, Long> {
	List<Notification> findByUser(User user);

	@Query("""
			SELECT n FROM Notification n
			WHERE
			(n.user.userId = :userId)
			OR
			(n.scope = 'ALL_USERS')
			OR
			(n.scope = 'GLOBAL')
			""")
	List<Notification> fetchUserNotifications(Long userId);

	@Query("""
			SELECT n FROM Notification n
			WHERE
			(n.citizen.citizenId = :citizenId)
			OR
			(n.scope = 'ALL_CITIZENS')
			OR
			(n.scope = 'GLOBAL')
			""")
	List<Notification> fetchCitizenNotifications(Long citizenId);
}
