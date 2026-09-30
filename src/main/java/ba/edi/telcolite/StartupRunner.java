package ba.edi.telcolite;

import ba.edi.telcolite.billing.PriceCalculator;
import ba.edi.telcolite.tariff.Tariff;
import ba.edi.telcolite.tariff.TariffRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class StartupRunner implements CommandLineRunner {

    private final TariffRepository tariffRepository;
    private final PriceCalculator priceCalculator;
    private final String operatorName;
    private final String currency;

    public StartupRunner(TariffRepository tariffRepository,
                         PriceCalculator priceCalculator,
                         @Value("${telcolite.operator-name}") String operatorName,
                         @Value("${telcolite.currency}") String currency) {
        this.tariffRepository = tariffRepository;
        this.priceCalculator = priceCalculator;
        this.operatorName = operatorName;
        this.currency = currency;
    }

    @Override
    public void run(String... args) {
        seedTariffs();
        printTariffs();
    }

    private void seedTariffs() {
        if (tariffRepository.count() > 0) {
            return;
        }
        tariffRepository.saveAll(List.of(
                new Tariff("BASIC", "Basic", 15.0, 300, 100, 5),
                new Tariff("SMART", "Smart", 30.0, 1000, 500, 20),
                new Tariff("UNLIMITED", "Unlimited", 50.0, 10000, 10000, 100)
        ));
        System.out.println("Initial tariffs created");
    }

    private void printTariffs() {
        System.out.println("=== " + operatorName + " ===");
        tariffRepository.findAll().stream()
                .sorted(Comparator.comparing(Tariff::getMonthlyPrice))
                .forEach(t -> System.out.printf("%-10s | %.2f %s -> %.2f %s%n",
                        t.getCode(),
                        t.getMonthlyPrice(), currency,
                        priceCalculator.finalMonthlyPrice(t), currency));
    }
}