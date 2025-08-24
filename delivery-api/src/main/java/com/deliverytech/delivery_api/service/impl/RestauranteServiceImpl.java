
package com.deliverytech.delivery_api.service.impl;

import org.springframework.stereotype.Service;
import com.deliverytech.delivery_api.service.RestauranteService;
import com.deliverytech.delivery_api.entity.Restaurante;
import com.deliverytech.delivery_api.repository.RestauranteRepository;
import java.util.List;
import java.math.BigDecimal;

@Service
public class RestauranteServiceImpl implements RestauranteService {

	private final com.deliverytech.delivery_api.repository.RestauranteRepository restauranteRepository;

	@org.springframework.beans.factory.annotation.Autowired
	public RestauranteServiceImpl(com.deliverytech.delivery_api.repository.RestauranteRepository restauranteRepository) {
		this.restauranteRepository = restauranteRepository;
	}

	@Override
	public List<com.deliverytech.delivery_api.entity.Restaurante> buscarPorCategoria(String categoria) {
		return restauranteRepository.findByCategoria(categoria);
	}

	@Override
	public List<com.deliverytech.delivery_api.entity.Restaurante> buscarAtivos() {
		return restauranteRepository.findByAtivoTrue();
	}

	@Override
	public List<com.deliverytech.delivery_api.entity.Restaurante> buscarPorTaxaEntregaMenorIgual(java.math.BigDecimal taxaEntrega) {
		return restauranteRepository.findByTaxaEntregaLessThanEqual(taxaEntrega);
	}

	@Override
	public List<com.deliverytech.delivery_api.entity.Restaurante> buscarTop5PorNomeAsc() {
		return restauranteRepository.findTop5ByOrderByNomeAsc();
	}

	@Override
	public List<com.deliverytech.delivery_api.entity.Restaurante> buscarPorNome(String nome) {
		return restauranteRepository.findByNome(nome);
	}
}