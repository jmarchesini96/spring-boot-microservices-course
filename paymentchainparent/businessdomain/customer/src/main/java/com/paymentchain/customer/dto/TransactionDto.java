package com.paymentchain.customer.dto;

import java.time.LocalDateTime;

public record TransactionDto (long id,
                             String reference,
                             String accountIban,
                             LocalDateTime date,
                             double amount,
                             double fee,
                             String description,
                             String status,
                             String channel) {}
