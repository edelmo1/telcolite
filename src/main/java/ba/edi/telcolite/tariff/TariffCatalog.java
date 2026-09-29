package ba.edi.telcolite.tariff;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TariffCatalog {


    private final List<TariffPlan> tariffs = List.of(
            new TariffPlan("BASIC", "Basic", 15.0, 300, 100, 5),
            new TariffPlan("SMART", "Smart", 30.0, 1000, 500, 20),
            new TariffPlan("UNLIMITED", "Unlimited", 50.0, 10000, 10000, 100)
    );

    public List<TariffPlan> findAll(){
        return tariffs;
    }

    public Optional<TariffPlan> findByCode(String code){
         return tariffs.stream().filter(t-> t.code().equals(code)).findFirst();
    }



}
