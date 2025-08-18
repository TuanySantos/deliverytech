package com.deliverytech.delivery_api.dto.requestDto;

import java.util.List;

public record PedidoRequestDTO(
    Long clienteId,
    Long restauranteId,
    List<ItemPedidoRequestDTO> itens
) {}