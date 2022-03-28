package com.amartya.queueservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.crypto.NoSuchPaddingException;
import java.io.FileNotFoundException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RepositoryRestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    TransactionRepository transactionRepository;

    @PostMapping("/transactions")
    public Transaction recordTransaction(@RequestBody Transaction transaction) throws FileNotFoundException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        String accountNumber = transaction.getAccountNumber();
        String accountFrom = transaction.getAccountFrom();

        Encryption encryption = new Encryption();
        transaction.setAccountFrom(encryption.decrypt(accountFrom));
        transaction.setAccountNumber(encryption.decrypt(accountNumber));
        transactionRepository.save(transaction);

        return transaction;
    }
}
