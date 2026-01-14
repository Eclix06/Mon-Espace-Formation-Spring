package com.monespaceformation.backend.controller;

import com.monespaceformation.backend.model.Notification;
import com.monespaceformation.backend.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller pour gérer les notifications
 */
@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(originPatterns = {"http://localhost:5173", "https://*.vercel.app"})
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;

    /**
     * Récupérer toutes les notifications triées par date décroissante
     * GET /api/notifications
     */
    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotifications() {
        try {
            List<Notification> notifications = notificationRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Notification::getCreatedAt).reversed())
                .collect(Collectors.toList());
            return ResponseEntity.ok(notifications);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Récupérer le nombre de notifications non lues
     * GET /api/notifications/unread/count
     */
    @GetMapping("/unread/count")
    public ResponseEntity<Long> getUnreadCount() {
        try {
            long count = notificationRepository.countByIsReadFalse();
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Marquer toutes les notifications comme lues
     * PUT /api/notifications/mark-all-read
     */
    @PutMapping("/mark-all-read")
    public ResponseEntity<?> markAllAsRead() {
        try {
            List<Notification> unreadNotifications = notificationRepository.findByIsReadFalse();
            for (Notification notification : unreadNotifications) {
                notification.setRead(true);
                notificationRepository.save(notification);
            }
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Marquer une notification spécifique comme lue
     * PUT /api/notifications/{id}/read
     */
    @PutMapping("/{id}/read")
    public ResponseEntity<?> markAsRead(@PathVariable String id) {
        try {
            if (id == null) {
                return ResponseEntity.badRequest().build();
            }
            Notification notification = notificationRepository.findById(id).orElse(null);
            if (notification != null) {
                notification.setRead(true);
                notificationRepository.save(notification);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}

