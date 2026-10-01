package com.paymentchain.product.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
public class Product {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    @NotBlank(message = "Code is required")
    private  String code;
    @NotBlank(message = "Name is required")
    private String name;

}
