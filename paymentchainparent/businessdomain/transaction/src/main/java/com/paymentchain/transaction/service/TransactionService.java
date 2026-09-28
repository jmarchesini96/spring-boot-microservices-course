package com.paymentchain.transaction.service;

import com.paymentchain.transaction.entities.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionService {

    List<Transaction> list();

    Optional<Transaction> findById(long id);

    Transaction save(Transaction transaction);

    Optional<Transaction> update(long id, Transaction transaction);

    boolean delete(long id);

    List<Transaction> findByAccountIban(String accountIban);

}
