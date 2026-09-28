package com.paymentchain.billing.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long customerId;
    private String number;
    private String detail;
    private double amount;

}
