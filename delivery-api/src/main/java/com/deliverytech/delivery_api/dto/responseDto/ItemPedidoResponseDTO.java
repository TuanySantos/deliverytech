package com.deliverytech.delivery_api.dto.responseDto;

import java.math.BigDecimal;

public record ItemPedidoResponseDTO(
    String produtoNome,
    int quantidade,
    BigDecimal precoTotal
) {}
