package com.deliverytech.delivery_api.service;

import com.deliverytech.delivery_api.dto.requestDto.PedidoRequestDTO;
import com.deliverytech.delivery_api.dto.responseDto.PedidoResponseDTO;
import com.deliverytech.delivery_api.enums.StatusPedido;
import java.time.LocalDateTime;
import java.util.List;

public interface PedidoService {
	List<PedidoResponseDTO> buscarPorClienteId(Long clienteId);
	List<PedidoResponseDTO> buscarPorStatus(StatusPedido status);
	List<PedidoResponseDTO> buscarTop10Recentes();
	List<PedidoResponseDTO> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim);

	PedidoResponseDTO salvar(PedidoRequestDTO dto);
	PedidoResponseDTO buscarPorId(Long id);
	PedidoResponseDTO atualizar(Long id, PedidoRequestDTO dto);
	void deletar(Long id);
}
