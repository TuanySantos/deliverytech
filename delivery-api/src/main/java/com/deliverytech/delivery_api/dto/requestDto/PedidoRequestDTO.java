package com.deliverytech.delivery_api.dto.requestDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record PedidoRequestDTO(
    @NotNull 
    Long clienteId,
    
    @NotNull 
    Long restauranteId,
    
    @NotEmpty 
    String enderecoEntrega,
    
    @NotEmpty()
    @Valid
    List<ItemPedidoRequestDTO> itens
) {}