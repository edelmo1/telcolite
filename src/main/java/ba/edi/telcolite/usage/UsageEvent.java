package ba.edi.telcolite.usage;

import java.io.Serializable;

public record UsageEvent(String phoneNumber, UsageType type, int amount) implements Serializable {
}