package ba.edi.telcolite.usage;

import java.time.YearMonth;

public record UsageSummaryResponse(Long subscriptionId,
                                   String phoneNumber,
                                   String tariffCode,
                                   YearMonth month,
                                   UsageItem minutes,
                                   UsageItem sms,
                                   UsageItem data) {
}