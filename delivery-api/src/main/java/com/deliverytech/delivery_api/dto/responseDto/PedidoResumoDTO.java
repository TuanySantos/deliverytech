package com.deliverytech.delivery_api.dto.responseDto;

import java.math.BigDecimal;
import com.deliverytech.delivery_api.enums.StatusPedido;

public record PedidoResumoDTO(
    Long id,
    String clienteNome,
    String restauranteNome,
    BigDecimal valorTotal,
    StatusPedido status
) {}
