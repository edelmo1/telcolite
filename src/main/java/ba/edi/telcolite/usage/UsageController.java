package ba.edi.telcolite.usage;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.YearMonth;

@RestController
@RequestMapping("/api/subscriptions/{subscriptionId}/usage")
public class UsageController {

    private final UsageService usageService;

    public UsageController(UsageService usageService) {
        this.usageService = usageService;
    }

    @PostMapping
    public ResponseEntity<UsageRecordResponse> record(@PathVariable Long subscriptionId,
                                                      @RequestBody @Valid RecordUsageRequest request) {
        UsageRecordResponse created = usageService.record(subscriptionId, request);
        URI location = URI.create("/api/subscriptions/%d/usage/%d".formatted(subscriptionId, created.id()));
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping
    public UsageSummaryResponse summary(@PathVariable Long subscriptionId,
                                        @RequestParam(required = false) String month) {
        YearMonth yearMonth = (month == null) ? YearMonth.now() : YearMonth.parse(month);
        return usageService.summary(subscriptionId, yearMonth);
    }
}