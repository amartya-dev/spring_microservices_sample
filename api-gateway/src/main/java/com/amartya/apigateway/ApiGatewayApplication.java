package com.amartya.apigateway;

import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import javax.crypto.NoSuchPaddingException;
import java.io.FileNotFoundException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableDiscoveryClient
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

}

@Data
class Transaction {
	private String accountNumber;
	private String type;
	private Double amount;
	private String currency;
	private String accountFrom;
}

@FeignClient("queue-service")
interface TransactionClient {
	@GetMapping("/transactions")
	@CrossOrigin
	CollectionModel<Transaction> readTransactions();

	@PostMapping("/transactions")
	@CrossOrigin
	Transaction writeTransaction(@RequestBody Transaction transaction);
}

@RestController
class TransactionController {
	private final TransactionClient transactionClient;

	public TransactionController (TransactionClient transactionClient) {
		this.transactionClient = transactionClient;
	}

	@GetMapping("/all-transactions")
	@CrossOrigin
	public Collection<Transaction> allTransactinos() {
		return new ArrayList<>(transactionClient.readTransactions()
				.getContent());
	}

	@PostMapping("/create-transaction")
	@CrossOrigin
	public Transaction postTransaction(@RequestBody Transaction transaction) throws FileNotFoundException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
		// Encrypt the transactions
		String accountFrom = transaction.getAccountFrom();
		String accountNumber = transaction.getAccountNumber();
		Encryption encryption = new Encryption();

		transaction.setAccountFrom(encryption.encrypt(accountFrom));
		transaction.setAccountNumber(encryption.encrypt(accountNumber));
		System.out.printf("Data being sent it Account From %s\n Account Number %s\n", transaction.getAccountFrom(), transaction.getAccountNumber());
		return transactionClient.writeTransaction(transaction);
	}
}
