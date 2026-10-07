package ba.edi.telcolite.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SmsChannel implements NotificationChannel{

    private static final Logger log = LoggerFactory.getLogger(SmsChannel.class);

    @Override
    public String name(){
        return "Sms";
    }
    @Override
    public void send(String recipient, String message){
        log.info("[{}] {}: {}", name(),recipient,message);

    }
}
