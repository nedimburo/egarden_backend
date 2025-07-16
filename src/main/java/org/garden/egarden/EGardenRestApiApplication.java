package org.garden.egarden;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EGardenRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EGardenRestApiApplication.class, args);
	}
}
