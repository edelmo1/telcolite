package ba.edi.telcolite.billing;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DiscountConfig {

    @Bean
    public DiscountPolicy discount(@Value("${telcolite.discount.type}") String type){

        if(type.equals("student")){
            return new StudentDiscount();
        }
        return new NoDiscount();
    }
}
