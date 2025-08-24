package com.deliverytech.delivery_api.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.deliverytech.delivery_api.dto.requestDto.RestauranteRequestDTO;
import com.deliverytech.delivery_api.dto.responseDto.RestauranteResponseDTO;
@Service
public interface RestauranteService {
	List<RestauranteResponseDTO> buscarPorCategoria(String categoria);
	List<RestauranteResponseDTO> buscarAtivos();
	List<RestauranteResponseDTO> buscarPorTaxaEntregaMenorIgual(java.math.BigDecimal taxaEntrega);
	List<RestauranteResponseDTO> buscarTop5PorNomeAsc();
	List<RestauranteResponseDTO> buscarPorNome(String nome);

	RestauranteResponseDTO salvar(RestauranteRequestDTO dto);
	RestauranteResponseDTO buscarPorId(Long id);
	RestauranteResponseDTO atualizar(Long id, RestauranteRequestDTO dto);
	void deletar(Long id);
}