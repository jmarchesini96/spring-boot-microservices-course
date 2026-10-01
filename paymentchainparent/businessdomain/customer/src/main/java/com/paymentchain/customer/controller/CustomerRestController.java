package com.paymentchain.customer.controller;

import com.paymentchain.customer.dto.ProductDto;
import com.paymentchain.customer.dto.TransactionDto;
import com.paymentchain.customer.entities.Customer;
import com.paymentchain.customer.repository.CustomerRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerRestController {

    private static final String URI_API_PRODUCTOS = "http://localhost:8082/product";
    private static final String URI_API_TRANSACCIONES = "http://localhost:8083/transaction";

    @Autowired
    CustomerRepository customerRepository;

    private final WebClient.Builder webClientBuilder;

    public CustomerRestController(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    HttpClient client = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            .option(ChannelOption.SO_KEEPALIVE, true) // Keep-Alive estándar multiplataforma
            .responseTimeout(Duration.ofSeconds(1))
            .doOnConnected(connection -> connection
                    .addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                    .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS))
            );

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
    public ResponseEntity<Customer> put(@PathVariable long id, @Valid @RequestBody Customer input) {
        Optional<Customer> customer = customerRepository.findById(id);

        return customer.map(existingCustomer -> {
            // 1. Mapeamos los datos nuevos sobre el cliente existente
            existingCustomer.setName(input.getName());
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
                    if (input.getName() != null) {
                        existingCustomer.setName(input.getName());
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
    public ResponseEntity<Customer> post(@Valid @RequestBody Customer input) {
        Optional.ofNullable(input.getProducts())
                .ifPresent(products -> products.forEach(p -> p.setCustomer(input)));
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

    @GetMapping("/full")
    public ResponseEntity<Customer> getByCode(@RequestParam String code) {
        return customerRepository.findByCode(code)
                .map(customer -> {
                    Optional.ofNullable(customer.getProducts())
                            .ifPresent(products -> products.forEach(product ->
                                    product.setProductName(getProductName(product.getProductId()))
                            ));

                    if (customer.getIban() != null && !customer.getIban().isBlank()) {
                        List<TransactionDto> transactions = getTransactions(customer.getIban());
                        customer.setTransactions(transactions);
                    }

                    return ResponseEntity.ok(customer);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private String getProductName(String productId) {
        if (productId == null || productId.isBlank()) {
            return "Unknown / Unavailable";
        }

        try {
            WebClient clientWeb = webClientBuilder.clientConnector(new ReactorClientHttpConnector(client))
                    .baseUrl(URI_API_PRODUCTOS)
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .build();

            ProductDto product = clientWeb.get()
                    .uri("/{id}", productId)
                    .retrieve()
                    .bodyToMono(ProductDto.class)
                    .block();

            return (product != null && product.name() != null) ? product.name() : "Unknown / Unavailable";
        } catch (Exception e) {
            System.err.println(">>> ERROR LLAMANDO A PRODUCT SERVICE (ID " + productId + "): " + e.getMessage());
            return "Unknown / Unavailable";
        }
    }

    private List<TransactionDto> getTransactions(String iban) {
        try {
            WebClient clientWeb = webClientBuilder.clientConnector(new ReactorClientHttpConnector(client))
                    .baseUrl(URI_API_TRANSACCIONES)
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .build();

            return clientWeb.get()
                    .uri(uriBuilder -> {
                        if (iban != null && !iban.isBlank()) {
                            return uriBuilder.queryParam("iban", iban).build();
                        }
                        return uriBuilder.build();
                    })
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<TransactionDto>>() {})
                    .block();
        } catch (Exception e) {
            System.err.println(">>> ERROR LLAMANDO A TRANSACTION SERVICE: " + e.getMessage());
            return Collections.emptyList();
        }
    }

}
