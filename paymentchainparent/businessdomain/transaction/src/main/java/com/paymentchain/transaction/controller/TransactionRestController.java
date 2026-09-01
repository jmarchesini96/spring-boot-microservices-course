package com.paymentchain.transaction.controller;

import com.paymentchain.transaction.entities.Transaction;
import com.paymentchain.transaction.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transaction")
public class TransactionRestController {

    @Autowired
    TransactionRepository transactionRepository;

    @GetMapping
    public ResponseEntity<List<Transaction>> list() {
        List<Transaction> transactions = transactionRepository.findAll();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> get(@PathVariable long id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        return transaction.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaction> put(@PathVariable long id, @RequestBody Transaction input) {
        Optional<Transaction> transaction = transactionRepository.findById(id);

        return transaction.map(existingTransaction -> {
            existingTransaction.setReference(input.getReference());
            existingTransaction.setAccountIban(input.getAccountIban());
            existingTransaction.setDate(input.getDate());
            existingTransaction.setAmount(input.getAmount());
            existingTransaction.setFee(input.getFee());
            existingTransaction.setDescription(input.getDescription());
            existingTransaction.setStatus(input.getStatus());
            existingTransaction.setChannel(input.getChannel());

            Transaction updatedTransaction = transactionRepository.save(existingTransaction);
            return ResponseEntity.ok(updatedTransaction);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Transaction> post(@RequestBody Transaction input) {
        Transaction savedTransaction = transactionRepository.save(input);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedTransaction.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedTransaction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        return transactionRepository.findById(id)
                .map(transaction -> {
                    transactionRepository.delete(transaction);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
