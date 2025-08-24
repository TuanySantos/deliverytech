package com.deliverytech.delivery_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import java.util.List;
import com.deliverytech.delivery_api.dto.requestDto.PedidoRequestDTO;
import com.deliverytech.delivery_api.dto.responseDto.PedidoResponseDTO;
import com.deliverytech.delivery_api.enums.StatusPedido;
import com.deliverytech.delivery_api.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	private final PedidoService pedidoService;

	public PedidoController(PedidoService pedidoService) {
		this.pedidoService = pedidoService;
	}

	@GetMapping("/cliente/{clienteId}")
	public ResponseEntity<List<PedidoResponseDTO>> buscarPorClienteId(@PathVariable Long clienteId) {
		List<PedidoResponseDTO> pedidos = pedidoService.buscarPorClienteId(clienteId);
		if (pedidos == null || pedidos.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(pedidos);
	}

	@GetMapping("/status/{status}")
	public ResponseEntity<List<PedidoResponseDTO>> buscarPorStatus(@PathVariable StatusPedido status) {
		List<PedidoResponseDTO> pedidos = pedidoService.buscarPorStatus(status);
		if (pedidos == null || pedidos.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(pedidos);
	}

	@GetMapping("/recentes")
	public ResponseEntity<List<PedidoResponseDTO>> buscarTop10Recentes() {
		List<PedidoResponseDTO> pedidos = pedidoService.buscarTop10Recentes();
		if (pedidos == null || pedidos.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(pedidos);
	}

	@GetMapping("/periodo")
	public ResponseEntity<List<PedidoResponseDTO>> buscarPorPeriodo(@RequestParam LocalDateTime inicio, @RequestParam LocalDateTime fim) {
		List<PedidoResponseDTO> pedidos = pedidoService.buscarPorPeriodo(inicio, fim);
		if (pedidos == null || pedidos.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(pedidos);
	}

	// CRUD
	@PostMapping
	public ResponseEntity<PedidoResponseDTO> criarPedido(@RequestBody PedidoRequestDTO dto) {
		PedidoResponseDTO salvo = pedidoService.salvar(dto);
		return ResponseEntity.status(201).body(salvo);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PedidoResponseDTO> buscarPorId(@PathVariable Long id) {
		PedidoResponseDTO pedido = pedidoService.buscarPorId(id);
		if (pedido != null) {
			return ResponseEntity.ok(pedido);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<PedidoResponseDTO> atualizarPedido(@PathVariable Long id, @RequestBody PedidoRequestDTO dto) {
		PedidoResponseDTO atualizado = pedidoService.atualizar(id, dto);
		if (atualizado != null) {
			return ResponseEntity.ok(atualizado);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarPedido(@PathVariable Long id) {
		pedidoService.deletar(id);
		return ResponseEntity.noContent().build();
	}
}
