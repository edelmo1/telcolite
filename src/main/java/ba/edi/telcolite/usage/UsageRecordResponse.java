package ba.edi.telcolite.usage;

import java.time.LocalDateTime;

public record UsageRecordResponse(Long id,
                                 Long subscriptionId,
                                 UsageType type,
                                 int amount,
                                 LocalDateTime recordedAt) {

   public static UsageRecordResponse from(UsageRecord record){
       return new UsageRecordResponse(
               record.getId(),
               record.getSubscription().getId(),
               record.getType(),
               record.getAmount(),
               record.getRecordedAt()
       );
   }

}
