package com.paymentchain.transactions.entities;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Transactions {

    private long id;
    private String reference;

}
