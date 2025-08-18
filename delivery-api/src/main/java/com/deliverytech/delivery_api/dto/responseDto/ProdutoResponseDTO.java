package com.deliverytech.delivery_api.dto.responseDto;

import java.math.BigDecimal;

public record ProdutoResponseDTO(
    Long id,
    String nome,
    String descricao,
    BigDecimal preco,
    String categoria,
    boolean disponivel,
    String restauranteNome
) {}
