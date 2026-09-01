package com.paymentchain.product.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Product {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    private  String code;
    private String name;

}
