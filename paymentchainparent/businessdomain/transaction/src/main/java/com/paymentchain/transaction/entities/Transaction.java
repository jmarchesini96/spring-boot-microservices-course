package com.paymentchain.transaction.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "transactions")
public class Transaction {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    private String reference;

    @NotBlank
    @Column(name = "account_iban", nullable = false)
    private String accountIban;

    private LocalDateTime date;
    private double amount;
    private double fee;
    private String description;

    @NotNull
    @Convert(converter = StatusConverter.class)
    @Column(name = "status", length = 2, nullable = false)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "channel", length = 20)
    private Channel channel;

}
