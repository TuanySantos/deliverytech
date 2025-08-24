package com.deliverytech.delivery_api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deliverytech.delivery_api.entity.Restaurante;
import com.deliverytech.delivery_api.mapper.RestauranteMapper;
import com.deliverytech.delivery_api.repository.RestauranteRepository;

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