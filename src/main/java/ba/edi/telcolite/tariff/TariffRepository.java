package ba.edi.telcolite.tariff;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TariffRepository extends JpaRepository<Tariff, String> {

    List<Tariff> findAllByMonthlyPriceLessThanEqual(double maxPrice);
}