package com.amartya.queueservice;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.mapping.CollectionResourceMapping;

import javax.persistence.*;
import javax.transaction.Transactional;

@Data
@NoArgsConstructor
@Entity
public class Transaction {

    public enum TransactionType {
        CREDIT, DEBIT
    }

    public Transaction(String accountNumber, TransactionType type, Double amount, String currency, String accountFrom) {
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.currency = currency;
        this.accountFrom = accountFrom;
    }

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String accountNumber;

    @NonNull
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @NonNull
    private Double amount;

    @NonNull
    private String currency;

    @NonNull
    private String accountFrom;
}

@RepositoryRestResource
interface TransactionRepository extends JpaRepository<Transaction, Long> {
}

