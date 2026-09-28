package com.paymentchain.transaction.service;

import com.paymentchain.transaction.entities.Status;
import com.paymentchain.transaction.entities.Transaction;
import com.paymentchain.transaction.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService{

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transaction> list() {
        return transactionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Transaction> findById(long id) {
        return transactionRepository.findById(id);
    }

    @Override
    @Transactional
    public Transaction save(Transaction transaction) {
        // Regla 1: El monto de la transacción no puede ser 0
        if (transaction.getAmount() == 0.0) {
            throw new IllegalArgumentException("El monto de la transacción no puede ser cero.");
        }

        // Regla 2: Si la comisión es mayor a 0, se deduce del monto
        if (transaction.getFee() > 0.0) {
            transaction.setAmount(transaction.getAmount() - transaction.getFee());
        }

        // 3. Validar que el monto neto resultante no sea cero
        if (transaction.getAmount() == 0.0) {
            throw new IllegalArgumentException("El monto neto de la transacción no puede ser cero tras deducir la comisión.");
        }

        // Regla 4: Manejo de fecha y asignación de estado
        LocalDateTime now = LocalDateTime.now();
        if (transaction.getDate() == null) {
            transaction.setDate(now);
        }

        if (transaction.getDate().isAfter(now)) {
            transaction.setStatus(Status.PENDIENTE);
        } else {
            transaction.setStatus(Status.LIQUIDADA);
        }

        return transactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public Optional<Transaction> update(long id, Transaction transaction) {
        return transactionRepository.findById(id)
                .map(existingTransaction -> {
                    existingTransaction.setReference(transaction.getReference());
                    existingTransaction.setAccountIban(transaction.getAccountIban());
                    existingTransaction.setDate(transaction.getDate());
                    existingTransaction.setAmount(transaction.getAmount());
                    existingTransaction.setFee(transaction.getFee());
                    existingTransaction.setDescription(transaction.getDescription());
                    existingTransaction.setStatus(transaction.getStatus());
                    existingTransaction.setChannel(transaction.getChannel());

                    return transactionRepository.save(existingTransaction);
                });
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        return transactionRepository.findById(id)
                .map(transaction -> {
                    transactionRepository.delete(transaction);
                    return true;
                })
                .orElse(false);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transaction> findByAccountIban(String accountIban) {
        return transactionRepository.findByAccountIban(accountIban);
    }

}
