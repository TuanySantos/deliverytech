package com.deliverytech.delivery_api.service.impl;


import org.springframework.stereotype.Service;
import com.deliverytech.delivery_api.service.PedidoService;
import com.deliverytech.delivery_api.entity.Pedido;
import com.deliverytech.delivery_api.enums.StatusPedidoEnum;
import com.deliverytech.delivery_api.repository.PedidoRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
@Service
public class PedidoServiceImpl implements PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Override
	public List<Pedido> buscarPorClienteId(Long clienteId) {
		return pedidoRepository.findByClienteId(clienteId);
	}

	@Override
	public List<Pedido> buscarPorStatus(StatusPedidoEnum status) {
		return pedidoRepository.findByStatus(status);
	}

	@Override
	public List<Pedido> buscarTop10Recentes() {
		return pedidoRepository.findTop10ByOrderByDataPedidoDesc();
	}

	@Override
	public List<Pedido> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
		return pedidoRepository.findByDataPedidoBetween(inicio, fim);
	}
}