package com.deliverytech.delivery_api.dto.requestDto;

public record ClienteRequestDTO(
    String nome,
    String email,
    String senha,
    String endereco,
    String telefone
) {}
