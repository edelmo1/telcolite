package ba.edi.telcolite.tariff;

import ba.edi.telcolite.billing.PriceCalculator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@Transactional(readOnly = true)
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
        List<Tariff> tariffs = (maxPrice == null)
                ? tariffRepository.findAll()
                : tariffRepository.findAllByMonthlyPriceLessThanEqual(maxPrice);

        return tariffs.stream()
                .sorted(Comparator.comparing(Tariff::getMonthlyPrice))
                .map(TariffPlan::from)
                .toList();
    }

    public TariffPlan getByCode(String code) {
        return TariffPlan.from(getTariff(code));
    }

    public Tariff getTariff(String code) {
        return tariffRepository.findById(code.toUpperCase())
                .orElseThrow(() -> new TariffNotFoundException(code));
    }

    @Transactional
    public TariffPlan create(TariffPlan plan) {
        if (tariffRepository.existsById(plan.code().toUpperCase())) {
            throw new DuplicateTariffCodeException(plan.code());
        }
        Tariff saved = tariffRepository.save(plan.toEntity());
        return TariffPlan.from(saved);
    }

    @Transactional
    public TariffPlan update(String code, TariffPlan plan) {
        if (!code.equalsIgnoreCase(plan.code())) {
            throw new IllegalArgumentException(
                    "Code in URL (%s) does not match code in body (%s)".formatted(code, plan.code()));
        }
        Tariff tariff = getTariff(code);
        tariff.update(plan.name(), plan.monthlyPrice(),
                plan.includedMinutes(), plan.includedSms(), plan.includedGb());
        return TariffPlan.from(tariffRepository.save(tariff));
    }

    @Transactional
    public void delete(String code) {
        String id = code.toUpperCase();
        if (!tariffRepository.existsById(id)) {
            throw new TariffNotFoundException(code);
        }
        tariffRepository.deleteById(id);
    }

    public TariffPriceResponse price(String code) {
        Tariff tariff = getTariff(code);
        return new TariffPriceResponse(
                tariff.getCode(),
                tariff.getMonthlyPrice(),
                priceCalculator.finalMonthlyPrice(tariff),
                currency);
    }
}