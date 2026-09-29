package ba.edi.telcolite.tariff;

import ba.edi.telcolite.billing.PriceCalculator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/tariffs")
public class TariffController {

    private final TariffCatalog tariffCatalog;
    private final PriceCalculator priceCalculator;
    private final String currency;

    public TariffController(TariffCatalog tariffCatalog, PriceCalculator priceCalculator,
                            @Value("${telcolite.currency}") String currency) {
        this.tariffCatalog = tariffCatalog;
        this.priceCalculator = priceCalculator;
        this.currency = currency;
    }

    @GetMapping("/{code}")
    public ResponseEntity<TariffPlan> findByCode(@PathVariable String code) {
        return tariffCatalog.findByCode(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<TariffPlan> findAll(@RequestParam(required = false) Double maxPrice) {
        if (maxPrice == null) {
            return tariffCatalog.findAll();
        }
        return tariffCatalog.findAll().stream()
                .filter(t -> t.monthlyPrice() <= maxPrice)
                .toList();
    }

    @PostMapping
    public ResponseEntity<TariffPlan> create(@RequestBody TariffPlan plan) {

        if (tariffCatalog.existsByCode(plan.code())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        TariffPlan saved = tariffCatalog.save(plan);
        URI location = URI.create("/api/tariffs/" + saved.code());
        return ResponseEntity.created(location).body(saved);
    }

    @GetMapping("/{code}/price")
    public ResponseEntity<TariffPriceResponse> price(@PathVariable String code) {
        return tariffCatalog.findByCode(code)
                .map(plan -> new TariffPriceResponse(
                        plan.code(),
                        plan.monthlyPrice(),
                        priceCalculator.finalMonthlyPrice(plan),
                        currency))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{code}")
    public ResponseEntity<TariffPlan> update(@PathVariable String code,
                                             @RequestBody TariffPlan plan) {

        if (!plan.code().equalsIgnoreCase(code)) {
            return ResponseEntity.badRequest().build();
        }
        if (!tariffCatalog.existsByCode(code)) {
            return ResponseEntity.notFound().build();
        }
        TariffPlan saved = tariffCatalog.save(plan);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> delete(@PathVariable String code) {

        if (!tariffCatalog.existsByCode(code)) {
            return ResponseEntity.notFound().build();
        }
        tariffCatalog.deleteByCode(code);
        return ResponseEntity.noContent().build();
    }
}
