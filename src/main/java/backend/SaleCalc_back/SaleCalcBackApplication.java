package backend.SaleCalc_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class SaleCalcBackApplication {
	public static void main(String[] args) {
		SpringApplication.run(SaleCalcBackApplication.class, args);
	}
}
