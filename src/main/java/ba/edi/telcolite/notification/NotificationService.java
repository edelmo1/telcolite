package ba.edi.telcolite.notification;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public static final String QUEUE = "telcolite.notifications";

    private final JmsTemplate jmsTemplate;

    public NotificationService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void broadcast(String recipient, String message) {

        jmsTemplate.convertAndSend(QUEUE,new NotificationMessage(recipient,message));
    }
}
