package com.deliverytech.delivery_api.service;

import java.math.BigDecimal;
import java.util.List;

import com.deliverytech.delivery_api.dto.requestDto.ProdutoRequestDTO;
import com.deliverytech.delivery_api.dto.responseDto.ProdutoResponseDTO;
import com.deliverytech.delivery_api.projection.ProdutoMaisVendidoProjection;

public interface ProdutoService {
	List<ProdutoResponseDTO> buscarPorRestauranteId(Long restauranteId);
	List<ProdutoResponseDTO> buscarDisponiveis();
	List<ProdutoResponseDTO> buscarPorCategoria(String categoria);
	List<ProdutoResponseDTO> buscarPorPrecoMenorIgual(BigDecimal preco);

	ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO dto);
	void deletar(Long id);
	List<ProdutoMaisVendidoProjection> listarProdutosMaisVendidos();
	
	ProdutoResponseDTO cadastrarProduto(ProdutoRequestDTO dto);

    List<ProdutoResponseDTO> buscarProdutosPorRestaurante(Long restauranteId);

    ProdutoResponseDTO buscarProdutoPorId(Long id);

    ProdutoResponseDTO atualizarProduto(Long id, ProdutoRequestDTO dto);

    void alterarDisponibilidade(Long id, boolean disponivel);

    List<ProdutoResponseDTO> buscarProdutosPorCategoria(String categoria);
}
