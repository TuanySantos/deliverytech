package com.deliverytech.delivery_api.dto.requestDto;

import java.math.BigDecimal;

public record RestauranteRequestDTO(
    String nome,
    String categoria,
    String endereco,
    BigDecimal taxaEntrega
) {}
