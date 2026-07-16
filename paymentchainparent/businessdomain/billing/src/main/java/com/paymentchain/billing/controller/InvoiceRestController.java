package com.paymentchain.billing.controller;

import com.paymentchain.billing.entities.Invoice;
import com.paymentchain.billing.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/invoice")
public class InvoiceRestController {

    @Autowired
    InvoiceRepository invoiceRepository;

    public ResponseEntity<List<Invoice>> list() {
        List<Invoice> invoices = invoiceRepository.findAll();
        return ResponseEntity.ok(invoices);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> get(@PathVariable  long id) {
        Optional<Invoice> invoice = invoiceRepository.findById(id);
        return invoice.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Invoice> put(@PathVariable long id, @RequestBody Invoice input) {
        Optional<Invoice> invoice = invoiceRepository.findById(id);

        return invoice.map(existingInvoice -> {
            existingInvoice.setCustomerId(input.getCustomerId());
            existingInvoice.setNumber(input.getNumber());
            existingInvoice.setDetail(input.getDetail());
            existingInvoice.setAmount(input.getAmount());

            Invoice updatedInvoice = invoiceRepository.save(existingInvoice);

            return ResponseEntity.ok(updatedInvoice);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Invoice> post(@RequestBody Invoice input) {
        Invoice savedInvoice = invoiceRepository.save(input);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedInvoice.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedInvoice);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        return invoiceRepository.findById(id)
                .map(invoice -> {
                    invoiceRepository.delete(invoice);
                    return ResponseEntity.ok().<Void>build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
