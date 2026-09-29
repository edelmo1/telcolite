package ba.edi.telcolite.notification;

public interface NotificationChannel {

    String name();
    void send(String recipient, String message);
}
