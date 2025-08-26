package com.deliverytech.delivery_api.dto.requestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record ProdutoRequestDTO(
    @NotBlank
    @Size(min = 2, max = 100)
    String nome,

    @NotBlank
    @Size(min = 5, max = 200)
    String descricao,

    @NotNull
    @DecimalMin(value = "0.01", inclusive = true)
    BigDecimal preco,

    @NotBlank
    @Size(min = 2, max = 50)
    String categoria,

    @NotNull
    Long restauranteId,

    @NotNull
    @DecimalMin(value = "0", inclusive = true)
    Integer estoque,

    @NotNull
    Boolean disponivel
) {}
