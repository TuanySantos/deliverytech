package com.deliverytech.delivery_api.dto.responseDto;

import java.time.LocalDateTime;
import java.util.List;

public record PedidoResponseDTO(
    Long id,
    String clienteNome,
    String restauranteNome,
    LocalDateTime dataPedido,
    List<ItemPedidoResponseDTO> itens,
    Integer tempoEstimadoEntrega
) {}