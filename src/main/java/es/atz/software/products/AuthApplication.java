package es.atz.software.products;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EntityScan("es.atz.software.products")
@EnableCaching
@EnableScheduling
@EnableAsync
@EnableRetry
public class AuthApplication {

	static {
		System.setProperty("java.awt.headless", "true");
	}

	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}

}