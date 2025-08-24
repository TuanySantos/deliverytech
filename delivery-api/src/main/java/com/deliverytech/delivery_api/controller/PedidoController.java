package com.deliverytech.delivery_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDateTime;
import java.util.List;
import com.deliverytech.delivery_api.entity.Pedido;
import com.deliverytech.delivery_api.enums.StatusPedidoEnum;
import com.deliverytech.delivery_api.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	private final PedidoService pedidoService;

	@Autowired
	public PedidoController(PedidoService pedidoService) {
		this.pedidoService = pedidoService;
	}

	@GetMapping("/cliente/{clienteId}")
	public List<Pedido> buscarPorClienteId(@PathVariable Long clienteId) {
		return pedidoService.buscarPorClienteId(clienteId);
	}

	@GetMapping("/status/{status}")
	public List<Pedido> buscarPorStatus(@PathVariable StatusPedidoEnum status) {
		return pedidoService.buscarPorStatus(status);
	}

	@GetMapping("/recentes")
	public List<Pedido> buscarTop10Recentes() {
		return pedidoService.buscarTop10Recentes();
	}

	@GetMapping("/periodo")
	public List<Pedido> buscarPorPeriodo(@RequestParam LocalDateTime inicio, @RequestParam LocalDateTime fim) {
		return pedidoService.buscarPorPeriodo(inicio, fim);
	}
}
