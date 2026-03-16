package com.cts.transpogov.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.cts.transpogov.models.Notification;

@Repository
public interface INotificationRepository extends JpaRepository<Notification, Long> {
    
    // Custom query to find notifications by the linked user ID
    @Query("SELECT n FROM Notification n WHERE n.user.userId = :userId ORDER BY n.createdDate DESC")
    List<Notification> findByUserId(@Param("userId") Long userId);
    
}