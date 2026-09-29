package ba.edi.telcolite.tariff;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TariffCatalog {


    private final Map<String, TariffPlan> tariffs = new ConcurrentHashMap<>();

    public TariffCatalog() {
        save(new TariffPlan("BASIC", "Basic", 15.0, 300, 100, 5));
        save(new TariffPlan("SMART", "Smart", 30.0, 1000, 500, 20));
        save(new TariffPlan("UNLIMITED", "Unlimited", 50.0, 10000, 10000, 100));
    }

    public List<TariffPlan> findAll() {
        return tariffs.values().stream()
                .sorted(Comparator.comparing(TariffPlan::monthlyPrice))
                .toList();
    }

    public Optional<TariffPlan> findByCode(String code) {
        return Optional.ofNullable(tariffs.get(code.toUpperCase()));
    }

    public boolean existsByCode(String code) {
        return tariffs.containsKey(code.toUpperCase());
    }

    public TariffPlan save(TariffPlan plan) {
        tariffs.put(plan.code().toUpperCase(), plan);
        return plan;
    }

    public boolean deleteByCode(String code) {
        return tariffs.remove(code.toUpperCase()) != null;
    }


}
