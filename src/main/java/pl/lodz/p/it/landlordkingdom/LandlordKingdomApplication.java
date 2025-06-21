package pl.lodz.p.it.landlordkingdom;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableAsync
@EnableScheduling
@EnableAspectJAutoProxy
@EnableRetry
@EnableWebMvc
public class LandlordKingdomApplication {

    public static void main(String[] args) {
        SpringApplication.run(LandlordKingdomApplication.class, args);
    }
}
