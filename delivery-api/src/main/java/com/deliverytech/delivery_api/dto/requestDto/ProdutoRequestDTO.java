package com.deliverytech.delivery_api.dto.requestDto;

import java.math.BigDecimal;

public record ProdutoRequestDTO(
    String nome,
    String descricao,
    BigDecimal preco,
    String categoria,
    Long restauranteId
) {}