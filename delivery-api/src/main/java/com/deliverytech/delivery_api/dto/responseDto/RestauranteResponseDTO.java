package com.deliverytech.delivery_api.dto.responseDto;

import java.math.BigDecimal;

public record RestauranteResponseDTO(
    Long id,
    String nome,
    String categoria,
    String endereco,
    BigDecimal taxaEntrega,
    boolean ativo
) {}
