package com.deliverytech.delivery_api.service;

import com.deliverytech.delivery_api.dto.requestDto.PedidoRequestDTO;
import com.deliverytech.delivery_api.dto.responseDto.PedidoResponseDTO;
import com.deliverytech.delivery_api.dto.requestDto.ItemPedidoRequestDTO;
import com.deliverytech.delivery_api.enums.StatusPedido;
import java.util.List;
import java.math.BigDecimal;

public interface PedidoService {
    PedidoResponseDTO criarPedido(PedidoRequestDTO dto);
    PedidoResponseDTO buscarPedidoPorId(Long id);
    List<PedidoResponseDTO> buscarPedidosPorCliente(Long clienteId);
    PedidoResponseDTO atualizarStatusPedido(Long id, StatusPedido status);
    BigDecimal calcularTotalPedido(List<ItemPedidoRequestDTO> itens);
    void cancelarPedido(Long id);
}
