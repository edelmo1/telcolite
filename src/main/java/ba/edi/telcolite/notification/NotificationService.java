package ba.edi.telcolite.notification;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final List<NotificationChannel> channels;

    public NotificationService(List<NotificationChannel> notifications) {
        this.channels = notifications;
    }

    public void broadcast(String recipient, String message) {

        for (NotificationChannel notification : channels) {
            notification.send(recipient, message);
        }
    }
}
