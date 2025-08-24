package com.deliverytech.delivery_api.exception;

public class ErroResponse {

    private final String tipo;
    private final String mensagem;

    public ErroResponse(String tipo, String mensagem) {
        this.tipo = tipo;
        this.mensagem = mensagem;
    }

    public String getTipo() {
        return tipo;
    }

    public String getMensagem() {
        return mensagem;
    }
}
