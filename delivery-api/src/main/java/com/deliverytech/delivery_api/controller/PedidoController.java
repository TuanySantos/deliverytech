package com.deliverytech.delivery_api.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.math.BigDecimal;
import com.deliverytech.delivery_api.dto.requestDto.PedidoRequestDTO;
import com.deliverytech.delivery_api.dto.requestDto.ItemPedidoRequestDTO;
import com.deliverytech.delivery_api.dto.responseDto.PedidoResponseDTO;
import com.deliverytech.delivery_api.enums.StatusPedido;
import com.deliverytech.delivery_api.service.PedidoService;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    // POST /api/pedidos
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> criarPedido(@RequestBody PedidoRequestDTO dto) {
        PedidoResponseDTO response = pedidoService.criarPedido(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // GET /api/pedidos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPedidoPorId(@PathVariable Long id) {
        PedidoResponseDTO response = pedidoService.buscarPedidoPorId(id);
        return ResponseEntity.ok(response);
    }

    // GET /api/clientes/{clienteId}/pedidos
    @GetMapping("/clientes/{clienteId}/pedidos")
    public ResponseEntity<List<PedidoResponseDTO>> buscarPedidosPorCliente(@PathVariable Long clienteId) {
        List<PedidoResponseDTO> pedidos = pedidoService.buscarPedidosPorCliente(clienteId);
        return ResponseEntity.ok(pedidos);
    }

    // PATCH /api/pedidos/{id}/status
    @PatchMapping("/{id}/status")
    public ResponseEntity<PedidoResponseDTO> atualizarStatusPedido(@PathVariable Long id, @RequestBody StatusPedido status) {
        PedidoResponseDTO response = pedidoService.atualizarStatusPedido(id, status);
        return ResponseEntity.ok(response);
    }

    // DELETE /api/pedidos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelarPedido(@PathVariable Long id) {
        pedidoService.cancelarPedido(id);
        return ResponseEntity.noContent().build();
    }

    // POST /api/pedidos/calcular
    @PostMapping("/calcular")
    public ResponseEntity<BigDecimal> calcularTotalPedido(@RequestBody List<ItemPedidoRequestDTO> itens) {
        BigDecimal total = pedidoService.calcularTotalPedido(itens);
        return ResponseEntity.ok(total);
    }
}
