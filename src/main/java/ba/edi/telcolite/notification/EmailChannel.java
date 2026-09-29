package ba.edi.telcolite.notification;

import org.springframework.stereotype.Component;

@Component
public class EmailChannel implements NotificationChannel{

    @Override
   public String name(){
        return "Email";
    }
    @Override
    public void send(String recipient, String message){
        System.out.printf("Recipient: %s ; message: %s",recipient,message);

    }

}
