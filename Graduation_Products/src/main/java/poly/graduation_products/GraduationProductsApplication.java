package poly.graduation_products;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GraduationProductsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraduationProductsApplication.class, args);
    }

}
