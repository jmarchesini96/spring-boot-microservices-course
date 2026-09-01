package com.paymentchain.transaction.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Status {
    PENDIENTE("01", "Pendiente"),
    LIQUIDADA("02", "Liquidada"),
    RECHAZADA("03", "Rechazada"),
    CANCELADA("04", "Cancelada");

    private final String code;
    private final String description;

    Status(String code, String description) {
        this.code = code;
        this.description = description;
    }

    // Le indica a Jackson y a JPA que serialicen/guarden el código "01", "02", etc.
    @JsonValue
    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    // Permite deserializar desde JSON cuando envían "01", "02", etc.
    @JsonCreator
    public static Status fromCode(String code) {
        for (Status status : Status.values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Código de estado desconocido: " + code);
    }
}
