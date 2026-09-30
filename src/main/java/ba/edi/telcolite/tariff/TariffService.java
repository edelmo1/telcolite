package ba.edi.telcolite.tariff;

import ba.edi.telcolite.billing.PriceCalculator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TariffService {

    private final TariffRepository tariffRepository;
    private final PriceCalculator priceCalculator;
    private final String currency;

    public TariffService(TariffRepository tariffRepository,
                         PriceCalculator priceCalculator,
                         @Value("${telcolite.currency}") String currency) {

        this.tariffRepository = tariffRepository;
        this.priceCalculator = priceCalculator;
        this.currency = currency;
    }

    public List<TariffPlan> findAll(Double maxPrice) {
        return tariffRepository.findAll().stream().filter(t -> maxPrice == null || t.monthlyPrice() <= maxPrice)
                .toList();
    }


    public TariffPlan create(TariffPlan plan) {
        if (tariffRepository.existsByCode(plan.code())) {
            throw new DuplicateTariffCodeException(plan.code());
        }
        return tariffRepository.save(plan);
    }

    public TariffPlan getByCode(String code) {
        return tariffRepository.findByCode(code)
                .orElseThrow(() -> new TariffNotFoundException(code));
    }

    public TariffPlan update(String code, TariffPlan plan) {

        if (!code.equalsIgnoreCase(plan.code())) {
            throw new IllegalArgumentException(
                    "Code in URL (%s) does not match code in body (%s)".formatted(code, plan.code()));
        }

        getByCode(code);
        return tariffRepository.save(plan);
    }

    public void delete(String code) {

        if (!tariffRepository.existsByCode(code)) {
            throw new TariffNotFoundException(code);
        }
        tariffRepository.deleteByCode(code);
    }

    public TariffPriceResponse price(String code) {
        TariffPlan plan = getByCode(code);
        return new TariffPriceResponse(plan.code(), plan.monthlyPrice(), priceCalculator.finalMonthlyPrice(plan), currency);
    }
}
