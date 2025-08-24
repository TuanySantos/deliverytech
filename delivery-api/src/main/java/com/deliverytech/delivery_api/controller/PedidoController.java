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
	public List<PedidoResponseDTO> buscarPorClienteId(@PathVariable Long clienteId) {
		return pedidoService.buscarPorClienteId(clienteId);
	}

	@GetMapping("/status/{status}")
	public List<PedidoResponseDTO> buscarPorStatus(@PathVariable StatusPedido status) {
		return pedidoService.buscarPorStatus(status);
	}

	@GetMapping("/recentes")
	public List<PedidoResponseDTO> buscarTop10Recentes() {
		return pedidoService.buscarTop10Recentes();
	}

	@GetMapping("/periodo")
	public List<PedidoResponseDTO> buscarPorPeriodo(@RequestParam LocalDateTime inicio, @RequestParam LocalDateTime fim) {
		return pedidoService.buscarPorPeriodo(inicio, fim);
	}

	// CRUD
	@PostMapping
	public ResponseEntity<PedidoResponseDTO> criarPedido(@RequestBody PedidoRequestDTO dto) {
		PedidoResponseDTO salvo = pedidoService.salvar(dto);
		return ResponseEntity.ok(salvo);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PedidoResponseDTO> buscarPorId(@PathVariable Long id) {
		PedidoResponseDTO pedido = pedidoService.buscarPorId(id);
		return ResponseEntity.ok(pedido);
	}

	@PutMapping("/{id}")
	public ResponseEntity<PedidoResponseDTO> atualizarPedido(@PathVariable Long id, @RequestBody PedidoRequestDTO dto) {
		PedidoResponseDTO atualizado = pedidoService.atualizar(id, dto);
		return ResponseEntity.ok(atualizado);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarPedido(@PathVariable Long id) {
		pedidoService.deletar(id);
		return ResponseEntity.noContent().build();
	}
}
