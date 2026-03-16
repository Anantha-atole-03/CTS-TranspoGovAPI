package com.cts.transpogov.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.cts.transpogov.dtos.notifications.*;
import com.cts.transpogov.service.INotificationService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private INotificationService notificationService;

    // 1) GET /notifications?userId=123
    @GetMapping("/get/{userId}")
    public ResponseEntity<List<NotificationResponse>> getUserNotifications(@RequestParam Long userId) {
        return ResponseEntity.ok(notificationService.getUserNotification(userId));
    }

    // 2) PATCH /notifications/{notificationId}
    @PatchMapping("/{notificationId}")
    public ResponseEntity<NotificationResponse> markAsRead(@PathVariable Long notificationId) {
        return ResponseEntity.ok(notificationService.markAsRead(notificationId));
    }

    // 3) POST /notifications
    @PostMapping
    public ResponseEntity<NotificationResponse> pushNotification(@Valid @RequestBody NotificationCreateRequest request) {
        return ResponseEntity.ok(notificationService.pushNotification(request));
    }
}