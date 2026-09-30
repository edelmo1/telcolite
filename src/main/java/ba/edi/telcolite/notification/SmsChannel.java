package ba.edi.telcolite.notification;

import org.springframework.stereotype.Component;

@Component
public class SmsChannel implements NotificationChannel{

    @Override
    public String name(){
        return "Sms";
    }
    @Override
    public void send(String recipient, String message){
        System.out.printf("Recipient: %s ; message: %s%n",recipient,message);

    }
}
