package com.deliverytech.delivery_api.dto.requestDto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

public record ItemPedidoRequestDTO(
    @NotNull
    Long produtoId,

    @NotNull
    @Min(1)
    int quantidade
) {}
