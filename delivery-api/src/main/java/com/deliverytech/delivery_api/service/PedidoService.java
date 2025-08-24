package com.deliverytech.delivery_api.service;

import com.deliverytech.delivery_api.entity.Pedido;
import com.deliverytech.delivery_api.enums.StatusPedidoEnum;
import java.time.LocalDateTime;
import java.util.List;

public interface PedidoService {
	List<Pedido> buscarPorClienteId(Long clienteId);
	List<Pedido> buscarPorStatus(StatusPedidoEnum status);
	List<Pedido> buscarTop10Recentes();
	List<Pedido> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim);
}
