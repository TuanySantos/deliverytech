package com.deliverytech.delivery_api.dto.requestDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClienteRequestDTO(
    @NotBlank
    @Size(min = 2, max = 100)
    String nome,

    @NotBlank
    @Email
    @Size(max = 100)
    String email,

    @NotBlank
    @Size(min = 6, max = 50)
    String senha,

    @NotBlank
    @Size(min = 5, max = 200)
    String endereco,

    @NotBlank
    @Size(min = 8, max = 20)
    String telefone
) {}
