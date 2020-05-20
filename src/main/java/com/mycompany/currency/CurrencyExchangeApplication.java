package com.mycompany.currency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.mycompany.currency.repository.UserRepository;

@SpringBootApplication
@ComponentScan
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class CurrencyExchangeApplication {


	public static void main(String[] args) {
		SpringApplication.run(CurrencyExchangeApplication.class, args);
	}

}
