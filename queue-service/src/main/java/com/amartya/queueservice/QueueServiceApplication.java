package com.amartya.queueservice;

import org.springframework.boot.ApplicationRunner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class QueueServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(QueueServiceApplication.class, args);
	}

	@Bean
	ApplicationRunner init(TransactionRepository repository) {
		return args -> {
			repository.save(new Transaction("122212", Transaction.TransactionType.CREDIT, 1212.12, "INR", "33333"));
			repository.save(new Transaction("122213", Transaction.TransactionType.CREDIT, 1412.12, "INR", "33343"));
			repository.save(new Transaction("122214", Transaction.TransactionType.DEBIT, 1252.12, "INR", "33335"));
			repository.save(new Transaction("122215", Transaction.TransactionType.CREDIT, 1112.12, "INR", "33363"));
			repository.save(new Transaction("122212", Transaction.TransactionType.DEBIT, 1217.12, "INR", "33333"));
			repository.findAll().forEach(System.out::println);
		};
	}

}
