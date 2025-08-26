package com.deliverytech.delivery_api.service;

import java.util.List;

import com.deliverytech.delivery_api.dto.requestDto.ClienteRequestDTO;
import com.deliverytech.delivery_api.dto.responseDto.ClienteResponseDTO;
import com.deliverytech.delivery_api.projection.RankingClienteProjection;

public interface ClienteService {
    ClienteResponseDTO cadastrarCliente(ClienteRequestDTO dto);
    ClienteResponseDTO buscarClientePorId(Long id);
    ClienteResponseDTO buscarClientePorEmail(String email);
    ClienteResponseDTO atualizarCliente(Long id, ClienteRequestDTO dto);
    void ativarDesativarCliente(Long id);
    List<ClienteResponseDTO> listarClientesAtivos();
    List<RankingClienteProjection> listarRankingClientesPorPedidos();
}
