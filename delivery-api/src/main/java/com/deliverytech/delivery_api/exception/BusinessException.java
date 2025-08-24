package com.deliverytech.delivery_api.exception;

import com.deliverytech.delivery_api.enums.ErroNegocio;

public class BusinessException extends RuntimeException {
    private final ErroNegocio tipo;

    public BusinessException(ErroNegocio tipo, String message) {
        super(message);
        this.tipo = tipo;
    }

    public ErroNegocio getTipo() {
        return tipo;
    }
}