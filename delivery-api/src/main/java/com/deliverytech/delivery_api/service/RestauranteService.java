package com.deliverytech.delivery_api.service;

import java.util.List;
import com.deliverytech.delivery_api.dto.requestDto.RestauranteRequestDTO;
import com.deliverytech.delivery_api.dto.responseDto.RestauranteResponseDTO;

public interface RestauranteService {
    RestauranteResponseDTO cadastrarRestaurante(RestauranteRequestDTO dto);
    RestauranteResponseDTO buscarRestaurantePorId(Long id);
    List<RestauranteResponseDTO> buscarRestaurantesPorCategoria(String categoria);
    List<RestauranteResponseDTO> buscarRestaurantesDisponiveis();
    RestauranteResponseDTO atualizarRestaurante(Long id, RestauranteRequestDTO dto);
    java.math.BigDecimal calcularTaxaEntrega(Long restauranteId, String cep);
}