package ba.edi.telcolite.notification;

import java.io.Serializable;

public record NotificationMessage(String recipient,
                                  String message)
        implements Serializable {
}
