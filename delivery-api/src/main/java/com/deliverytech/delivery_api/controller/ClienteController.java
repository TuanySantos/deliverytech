package com.deliverytech.delivery_api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.deliverytech.delivery_api.dto.requestDto.ClienteRequestDTO;
import com.deliverytech.delivery_api.dto.responseDto.ClienteResponseDTO;
import com.deliverytech.delivery_api.service.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> criarCliente(@RequestBody ClienteRequestDTO dto) {
        ClienteResponseDTO response = clienteService.salvar(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarCliente(@PathVariable Long id) {
        ClienteResponseDTO response = clienteService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarClientes() {
        List<ClienteResponseDTO> clientes = clienteService.listarTodos();
        return ResponseEntity.ok(clientes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> atualizarCliente(@PathVariable Long id, @RequestBody ClienteRequestDTO dto) {
        ClienteResponseDTO response = clienteService.atualizar(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Long id) {
        clienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // Consultas customizadas
    @GetMapping("/email/{email}")
    public ResponseEntity<ClienteResponseDTO> buscarPorEmail(@PathVariable String email) {
        ClienteResponseDTO response = clienteService.buscarPorEmail(email);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<ClienteResponseDTO>> listarAtivos() {
        List<ClienteResponseDTO> clientes = clienteService.listarAtivos();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<ClienteResponseDTO> buscarPorNome(@PathVariable String nome) {
        ClienteResponseDTO response = clienteService.buscarPorNome(nome);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/existe-email/{email}")
    public ResponseEntity<Boolean> existePorEmail(@PathVariable String email) {
        boolean existe = clienteService.existePorEmail(email);
        return ResponseEntity.ok(existe);
    }
}
