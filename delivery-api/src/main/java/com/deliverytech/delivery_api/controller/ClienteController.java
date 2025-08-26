package com.deliverytech.delivery_api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.deliverytech.delivery_api.dto.requestDto.ClienteRequestDTO;
import com.deliverytech.delivery_api.dto.responseDto.ClienteResponseDTO;
import com.deliverytech.delivery_api.service.ClienteService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // POST /api/clientes
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> cadastrarCliente(@RequestBody ClienteRequestDTO dto) {
        ClienteResponseDTO response = clienteService.cadastrarCliente(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // GET /api/clientes/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarPorId(@PathVariable Long id) {
        ClienteResponseDTO response = clienteService.buscarClientePorId(id);
        return ResponseEntity.ok(response);
    }

    // GET /api/clientes
    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarClientesAtivos() {
        List<ClienteResponseDTO> clientes = clienteService.listarClientesAtivos();
        return ResponseEntity.ok(clientes);
    }

    // PUT /api/clientes/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> atualizarCliente(@PathVariable Long id, @RequestBody ClienteRequestDTO dto) {
        ClienteResponseDTO response = clienteService.atualizarCliente(id, dto);
        return ResponseEntity.ok(response);
    }

    // PATCH /api/clientes/{id}/status
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> ativarDesativarCliente(@PathVariable Long id) {
        clienteService.ativarDesativarCliente(id);
        return ResponseEntity.noContent().build();
    }

    // GET /api/clientes/email/{email}
    @GetMapping("/email/{email}")
    public ResponseEntity<ClienteResponseDTO> buscarPorEmail(@PathVariable String email) {
        ClienteResponseDTO response = clienteService.buscarClientePorEmail(email);
        return ResponseEntity.ok(response);
    }
}
