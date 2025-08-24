package com.deliverytech.delivery_api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deliverytech.delivery_api.entity.Restaurante;
import com.deliverytech.delivery_api.mapper.RestauranteMapper;
import com.deliverytech.delivery_api.repository.RestauranteRepository;


@Service
public interface RestauranteService {
	List<Restaurante> buscarPorCategoria(String categoria);
	List<Restaurante> buscarAtivos();
	List<Restaurante> buscarPorTaxaEntregaMenorIgual(java.math.BigDecimal taxaEntrega);
	List<Restaurante> buscarTop5PorNomeAsc();
	List<Restaurante> buscarPorNome(String nome);
}