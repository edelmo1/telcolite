package ba.edi.telcolite.simulator;

import ba.edi.telcolite.usage.UsageEvent;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/simulator")
public class NetworkSimulatorController {

    private final JmsTemplate jmsTemplate;

    public NetworkSimulatorController(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @PostMapping("/usage")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void simulate(@Valid @RequestBody UsageEvent event) {
        jmsTemplate.convertAndSend("telcolite.usage", event);
    }
}