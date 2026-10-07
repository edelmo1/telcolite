package ba.edi.telcolite;

import ba.edi.telcolite.billing.PriceCalculator;
import ba.edi.telcolite.customer.Customer;
import ba.edi.telcolite.customer.CustomerRepository;
import ba.edi.telcolite.customer.Role;
import ba.edi.telcolite.tariff.Tariff;
import ba.edi.telcolite.tariff.TariffRepository;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class StartupRunner implements CommandLineRunner {

    private static final String ADMIN_EMAIL = "admin@telcolite.ba";

    private final TariffRepository tariffRepository;
    private final CustomerRepository customerRepository;
    private final PriceCalculator priceCalculator;
    private final String operatorName;
    private final String currency;
    private static final Logger log = LoggerFactory.getLogger(StartupRunner.class);
    private final PasswordEncoder passwordEncoder;

    public StartupRunner(TariffRepository tariffRepository,
                         CustomerRepository customerRepository,
                         PriceCalculator priceCalculator,
                         @Value("${telcolite.operator-name}") String operatorName,
                         @Value("${telcolite.currency}") String currency,
                         PasswordEncoder passwordEncoder) {
        this.tariffRepository = tariffRepository;
        this.customerRepository=customerRepository;
        this.priceCalculator = priceCalculator;
        this.operatorName = operatorName;
        this.currency = currency;
        this.passwordEncoder=passwordEncoder;
    }

    @Override
    public void run(String @NonNull ... args) {
        seedTariffs();
        printTariffs();
        seedAdmin();
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
        log.info("Initial tariffs created");
    }

    private void printTariffs() {
        log.info("=== {} ===", operatorName);
        tariffRepository.findAll().stream()
                .sorted(Comparator.comparing(Tariff::getMonthlyPrice))
                .forEach(t -> log.info("{} | {} {} -> {} {}",
                        t.getCode(),
                        "%.2f".formatted(t.getMonthlyPrice()), currency,
                        "%.2f".formatted(priceCalculator.finalMonthlyPrice(t)), currency));
    }

    private void seedAdmin() {
        if (customerRepository.existsByEmail(ADMIN_EMAIL)) {
            return;
        }
        customerRepository.save(new Customer(null, "Admin", "TelcoLite", ADMIN_EMAIL,
                passwordEncoder.encode("admin12345"), Role.ADMIN));
        log.info("Admin account created: {}", ADMIN_EMAIL);
    }
}