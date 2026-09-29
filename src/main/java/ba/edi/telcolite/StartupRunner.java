package ba.edi.telcolite;

import ba.edi.telcolite.billing.PriceCalculator;
import ba.edi.telcolite.notification.NotificationService;
import ba.edi.telcolite.tariff.TariffCatalog;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements CommandLineRunner {

    private final TariffCatalog tariffCatalog;
    private final PriceCalculator priceCalculator;
    private final NotificationService notificationService;
    private final String operatorName;

    public StartupRunner(TariffCatalog tariffCatalog,
                         PriceCalculator priceCalculator,
                         NotificationService notificationService,
                         @Value("${telcolite.operator-name}") String operatorName) {
        this.tariffCatalog = tariffCatalog;
        this.priceCalculator = priceCalculator;
        this.notificationService = notificationService;
        this.operatorName = operatorName;
    }


    @Override
    public void run(String... args) throws Exception {

        System.out.println("=== TelcoLite BiH ===");
        for(int i=0; i<tariffCatalog.findAll().size();i++){
            System.out.printf("%s     |%.2f KM  ->  %.2f KM%n",
                    tariffCatalog.findAll().get(i).code(),
                    tariffCatalog.findAll().get(i).monthlyPrice(),
                    priceCalculator.finalMonthlyPrice(tariffCatalog.findAll().get(i)));
        }
        notificationService.broadcast("edi@mail.com", "Dobrodošli u " + operatorName + "!");
    }
}
