package com.deliverytech.delivery_api.dto.responseDto;

public record ClienteResponseDTO(
    Long id,
    String nome,
    String email,
    String endereco,
    String telefone,
    boolean ativo
) {}