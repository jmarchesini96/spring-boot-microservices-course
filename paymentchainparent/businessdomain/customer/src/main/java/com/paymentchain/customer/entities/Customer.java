package com.paymentchain.customer.entities;

import com.paymentchain.customer.dto.TransactionDto;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Customer {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    private String code;
    private  String name;
    private String phone;
    private String iban;
    private String surname;
    private String address;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CustomerProduct> products;
    @Transient
    private List<TransactionDto> transactions;

}
