package ba.edi.telcolite.subscription;

public record CreateSubscriptionRequest(String tariffCode,
                                        String phoneNumber) {

    public CreateSubscriptionRequest{
        if(!phoneNumber.matches("06\\d{7,8}")){
            throw new IllegalArgumentException("Wrong phone number");
        }
    }
}
