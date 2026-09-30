package ba.edi.telcolite.tariff;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/tariffs")
public class TariffController {

    private final TariffService tariffService;

    public TariffController(TariffService tariffService) {
        this.tariffService = tariffService;
    }

    @GetMapping("/{code}")
    public TariffPlan findByCode(@PathVariable String code) {
        return tariffService.getByCode(code);
    }

    @GetMapping
    public List<TariffPlan> findAll(@RequestParam(required = false) Double maxPrice) {
        return tariffService.findAll(maxPrice);
    }

    @PostMapping
    public ResponseEntity<TariffPlan> create(@RequestBody TariffPlan plan) {
        TariffPlan created = tariffService.create(plan);
        return ResponseEntity.created(URI.create("/api/tariffs/" + created.code())).body(created);
    }

    @GetMapping("/{code}/price")
    public TariffPriceResponse price(@PathVariable String code) {

        return tariffService.price(code);
    }

    @PutMapping("/{code}")
    public TariffPlan update(@PathVariable String code,
                             @RequestBody TariffPlan plan) {

        return tariffService.update(code, plan);
    }

    @DeleteMapping("/{code}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String code) {
        tariffService.delete(code);
    }
}
