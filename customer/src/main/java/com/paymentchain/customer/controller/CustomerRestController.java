package com.paymentchain.customer.controller;

import com.paymentchain.customer.entities.Customer;
import com.paymentchain.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerRestController {

    @Autowired
    CustomerRepository customerRepository;

    @GetMapping
    public ResponseEntity<List<Customer>> list() {
        List<Customer> customers = customerRepository.findAll();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> get(@PathVariable long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> put(@PathVariable long id, @RequestBody Customer input) {
        Optional<Customer> customer = customerRepository.findById(id);

        return customer.map(existingCustomer -> {
            // 1. Mapeamos los datos nuevos sobre el cliente existente
            existingCustomer.setNombre(input.getNombre());
            existingCustomer.setPhone(input.getPhone());

            // 2. Guardamos los cambios en la base de datos
            Customer updatedCustomer = customerRepository.save(existingCustomer);

            // 3. Devolvemos el cliente actualizado con un 200 OK
            return ResponseEntity.ok(updatedCustomer);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Customer> patch(@PathVariable long id, @RequestBody Customer input) {
        return customerRepository.findById(id)
                .map(existingCustomer -> {
                    // Evaluamos campo por campo para actualizar SOLO lo que el cliente envió
                    if (input.getNombre() != null) {
                        existingCustomer.setNombre(input.getNombre());
                    }
                    if (input.getPhone() != null) {
                        existingCustomer.setPhone(input.getPhone());
                    }

                    Customer updatedCustomer = customerRepository.save(existingCustomer);
                    return ResponseEntity.ok(updatedCustomer);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Customer> post(@RequestBody Customer input) {
        Customer savedCustomer = customerRepository.save(input);

        // Construye la URI del nuevo recurso: /customers/{id}
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedCustomer.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        return customerRepository.findById(id)
                .map(customer -> {
                    customerRepository.delete(customer);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
