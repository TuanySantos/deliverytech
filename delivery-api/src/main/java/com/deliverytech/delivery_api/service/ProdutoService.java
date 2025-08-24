package com.deliverytech.delivery_api.service;

import java.math.BigDecimal;
import java.util.List;

import com.deliverytech.delivery_api.dto.requestDto.ProdutoRequestDTO;
import com.deliverytech.delivery_api.dto.responseDto.ProdutoResponseDTO;

public interface ProdutoService {
	List<ProdutoResponseDTO> buscarPorRestauranteId(Long restauranteId);
	List<ProdutoResponseDTO> buscarDisponiveis();
	List<ProdutoResponseDTO> buscarPorCategoria(String categoria);
	List<ProdutoResponseDTO> buscarPorPrecoMenorIgual(BigDecimal preco);

	ProdutoResponseDTO salvar(ProdutoRequestDTO dto);
	ProdutoResponseDTO buscarPorId(Long id);
	ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO dto);
	void deletar(Long id);
}
