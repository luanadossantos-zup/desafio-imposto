package com.catalisa.desafio_imposto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.catalisa.desafio_imposto")
@EnableJpaRepositories(basePackages = "com.catalisa.desafio_imposto.repository")
@EntityScan(basePackages = "com.catalisa.desafio_imposto.model")
public class DesafioImpostoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafioImpostoApplication.class, args);
	}

}
