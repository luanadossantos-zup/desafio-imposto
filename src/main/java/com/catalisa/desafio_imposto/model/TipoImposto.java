package com.catalisa.desafio_imposto.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoImposto {
    ICMS,
    ISS,
    IPI;

    @JsonCreator
    public static TipoImposto fromValue(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("O valor do nome não pode ser vazio ou nulo.");
        }
        try {
            return TipoImposto.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Valor inválido para nome: " + value);
        }
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}
