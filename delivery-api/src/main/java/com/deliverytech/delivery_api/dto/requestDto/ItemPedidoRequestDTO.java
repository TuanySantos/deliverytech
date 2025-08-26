package com.deliverytech.delivery_api.dto.requestDto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;

public record ItemPedidoRequestDTO(
    @NotNull
    Long produtoId,

    @NotNull
    @DecimalMin(value = "1")
    int quantidade
) {}
