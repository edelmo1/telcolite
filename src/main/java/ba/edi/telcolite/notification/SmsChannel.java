package ba.edi.telcolite.notification;

public class SmsChannel implements NotificationChannel{

    @Override
    public String name(){
        return "Sms";
    }
    @Override
    public void send(String recipient, String message){
        System.out.printf("Recipient: %s ; message: %s",recipient,message);

    }
}
