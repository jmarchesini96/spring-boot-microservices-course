package com.paymentchain.transaction.controller;

import com.paymentchain.transaction.entities.Transaction;
import com.paymentchain.transaction.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionRestController {

    private final TransactionService transactionService;

    public TransactionRestController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> list(@RequestParam(required = false) String iban) {
        if (iban != null && !iban.isBlank()) {
            return ResponseEntity.ok(transactionService.findByAccountIban(iban));
        }
        return ResponseEntity.ok(transactionService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> get(@PathVariable long id) {
        return transactionService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaction> put(@PathVariable long id, @Valid @RequestBody Transaction input) {
        return transactionService.update(id, input)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Transaction> post(@Valid @RequestBody Transaction input) {
        Transaction savedTransaction = transactionService.save(input);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedTransaction.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedTransaction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        if (transactionService.delete(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
