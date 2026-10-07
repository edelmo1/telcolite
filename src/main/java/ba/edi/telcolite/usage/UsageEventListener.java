package ba.edi.telcolite.usage;

import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class UsageEventListener {

    private static final Logger log = (Logger) LoggerFactory.getLogger(UsageEventListener.class);

    private final UsageService usageService;

    public UsageEventListener(UsageService usageService) {
        this.usageService = usageService;
    }

    @JmsListener(destination = "telcolite.usage")
    public void onUsage(UsageEvent event) {
        log.info("Usage event: {} {} {}");
        usageService.recordByPhoneNumber(event.phoneNumber(), event.type(), event.amount());
    }
}