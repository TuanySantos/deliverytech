package com.deliverytech.delivery_api.dto.requestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record RestauranteRequestDTO(
    @NotBlank
    @Size(min = 2, max = 100)
    String nome,

    @NotBlank
    @Size(min = 2, max = 50)
    String categoria,

    @NotBlank
    @Size(min = 5, max = 200)
    String endereco,

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    BigDecimal taxaEntrega
) {}
