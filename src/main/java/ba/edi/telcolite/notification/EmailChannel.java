package ba.edi.telcolite.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EmailChannel implements NotificationChannel{

    private static final Logger log = LoggerFactory.getLogger(EmailChannel.class);

    @Override
   public String name(){
        return "Email";
    }
    @Override
    public void send(String recipient, String message){
        log.info("[{}] {}: {}", name(),recipient,message);
    }

}
