package com.paymentchain.transaction.entities;

import jakarta.persistence.*;

@SuppressWarnings("unused")
@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<Status, String> {

    // Se ejecuta al GUARDAR en la base de datos (Java Enum -> BD String "01")
    @Override
    public String convertToDatabaseColumn(Status status) {
        if (status == null) {
            return null;
        }
        return status.getCode();
    }

    // Se ejecuta al LEER de la base de datos (BD String "01" -> Java Enum PENDIENTE)
    @Override
    public Status convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return Status.fromCode(dbData);
    }

}
