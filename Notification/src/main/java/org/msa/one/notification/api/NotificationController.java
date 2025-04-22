package org.msa.one.notification.api;

import org.msa.one.notification.service.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/event/booked")
    public void handleBookedEvent(@RequestParam String bookingId,
                                  @RequestParam String userId,
                                  @RequestParam String type,
                                  @RequestParam String channel) {
        notificationService.handleBookedEvent(
                UUID.fromString(bookingId),
                UUID.fromString(userId),
                type,
                channel
        );
    }
}