package ba.edi.telcolite.usage;

public record UsageItem(long used, long included, long remaining, String unit) {

    public static UsageItem of(long used, long included, String unit) {
        long remaining = Math.max(0, included - used);
        return new UsageItem(used, included, remaining, unit);
    }
}