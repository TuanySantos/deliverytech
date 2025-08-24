package com.deliverytech.delivery_api.service;

import java.util.List;

import com.deliverytech.delivery_api.dto.requestDto.ClienteRequestDTO;
import com.deliverytech.delivery_api.dto.responseDto.ClienteResponseDTO;

public interface ClienteService {
    ClienteResponseDTO salvar(ClienteRequestDTO clienteRequestDTO);
    ClienteResponseDTO buscarPorId(Long id);
    List<ClienteResponseDTO> listarTodos();
    ClienteResponseDTO atualizar(Long id, ClienteRequestDTO clienteRequestDTO);
    void deletar(Long id);

    ClienteResponseDTO buscarPorEmail(String email);
    List<ClienteResponseDTO> listarAtivos();
    ClienteResponseDTO buscarPorNome(String nome);
    boolean existePorEmail(String email);
}
