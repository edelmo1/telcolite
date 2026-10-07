package ba.edi.telcolite.usage;

public record RecordUsageRequest(UsageType type,
                                 int amount) {

    public RecordUsageRequest{
        if(type==null){
            throw new IllegalArgumentException("Usage type is required (CALL, SMS or DATA)");
        }
        if(amount<=0){
            throw new IllegalArgumentException("Amount must be positive, but was " + amount);
        }
    }
}
