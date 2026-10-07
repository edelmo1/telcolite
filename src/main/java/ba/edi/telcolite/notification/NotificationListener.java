package ba.edi.telcolite.notification;

import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Component
public class NotificationListener {

    private static final Logger log = (Logger) LoggerFactory.getLogger(NotificationListener.class);

    private final List<NotificationChannel> channels;

    public NotificationListener(List<NotificationChannel> channels) {
        this.channels = channels;
    }

    @JmsListener(destination = NotificationService.QUEUE)
    public void onNotification(NotificationMessage notification) {
        log.info("Received notification for {}");
        channels.forEach(c -> c.send(notification.recipient(), notification.message()));
    }
}