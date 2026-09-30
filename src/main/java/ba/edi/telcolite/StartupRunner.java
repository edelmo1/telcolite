package ba.edi.telcolite;

import ba.edi.telcolite.billing.PriceCalculator;
import ba.edi.telcolite.notification.NotificationService;
import ba.edi.telcolite.tariff.TariffRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements CommandLineRunner {

    private final TariffRepository tariffRepository;
    private final PriceCalculator priceCalculator;
    private final NotificationService notificationService;
    private final String operatorName;

    public StartupRunner(TariffRepository tariffRepository,
                         PriceCalculator priceCalculator,
                         NotificationService notificationService,
                         @Value("${telcolite.operator-name}") String operatorName) {
        this.tariffRepository = tariffRepository;
        this.priceCalculator = priceCalculator;
        this.notificationService = notificationService;
        this.operatorName = operatorName;
    }


    @Override
    public void run(String... args) throws Exception {

        System.out.println("=== TelcoLite BiH ===");
        for(int i = 0; i< tariffRepository.findAll().size(); i++){
            System.out.printf("%s     |%.2f KM  ->  %.2f KM%n",
                    tariffRepository.findAll().get(i).code(),
                    tariffRepository.findAll().get(i).monthlyPrice(),
                    priceCalculator.finalMonthlyPrice(tariffRepository.findAll().get(i)));
        }
        notificationService.broadcast("edi@mail.com", "Dobrodošli u " + operatorName + "!");
    }
}
