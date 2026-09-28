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

    @JsonCreator
    public static Status fromValue(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        for (Status status : Status.values()) {
            if (status.description.equalsIgnoreCase(value.trim())
                    || status.name().equalsIgnoreCase(value.trim())
                    || status.code.equalsIgnoreCase(value.trim())) {
                return status;
            }
        }
        throw new IllegalArgumentException("Estado desconocido: " + value);
    }
}
