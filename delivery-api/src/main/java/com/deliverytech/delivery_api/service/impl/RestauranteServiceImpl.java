
package com.deliverytech.delivery_api.service.impl;

import org.springframework.stereotype.Service;
import com.deliverytech.delivery_api.service.RestauranteService;
import com.deliverytech.delivery_api.entity.Restaurante;
import com.deliverytech.delivery_api.repository.RestauranteRepository;
import com.deliverytech.delivery_api.dto.requestDto.RestauranteRequestDTO;
import com.deliverytech.delivery_api.dto.responseDto.RestauranteResponseDTO;
import com.deliverytech.delivery_api.mapper.RestauranteMapper;
import java.util.List;
import java.math.BigDecimal;

@Service
public class RestauranteServiceImpl implements RestauranteService {

	private final RestauranteRepository restauranteRepository;
	private final RestauranteMapper restauranteMapper;

	@org.springframework.beans.factory.annotation.Autowired
	public RestauranteServiceImpl(RestauranteRepository restauranteRepository, RestauranteMapper restauranteMapper) {
		this.restauranteRepository = restauranteRepository;
		this.restauranteMapper = restauranteMapper;
	}

	@Override
	public List<RestauranteResponseDTO> buscarPorCategoria(String categoria) {
		return restauranteMapper.toResponseDtoList(restauranteRepository.findByCategoria(categoria));
	}

	@Override
	public List<RestauranteResponseDTO> buscarAtivos() {
		return restauranteMapper.toResponseDtoList(restauranteRepository.findByAtivoTrue());
	}

	@Override
	public List<RestauranteResponseDTO> buscarPorTaxaEntregaMenorIgual(BigDecimal taxaEntrega) {
		return restauranteMapper.toResponseDtoList(restauranteRepository.findByTaxaEntregaLessThanEqual(taxaEntrega));
	}

	@Override
	public List<RestauranteResponseDTO> buscarTop5PorNomeAsc() {
		return restauranteMapper.toResponseDtoList(restauranteRepository.findTop5ByOrderByNomeAsc());
	}

	@Override
	public List<RestauranteResponseDTO> buscarPorNome(String nome) {
		return restauranteMapper.toResponseDtoList(restauranteRepository.findByNome(nome));
	}

	@Override
	public RestauranteResponseDTO salvar(RestauranteRequestDTO dto) {
		Restaurante restaurante = restauranteMapper.toEntity(dto);
		Restaurante salvo = restauranteRepository.save(restaurante);
		return restauranteMapper.toResponseDto(salvo);
	}

	@Override
	public RestauranteResponseDTO buscarPorId(Long id) {
		Restaurante restaurante = restauranteRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));
		return restauranteMapper.toResponseDto(restaurante);
	}

	@Override
	public RestauranteResponseDTO atualizar(Long id, RestauranteRequestDTO dto) {
		Restaurante restaurante = restauranteRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));
		restaurante.setNome(dto.nome());
		restaurante.setCategoria(dto.categoria());
		restaurante.setEndereco(dto.endereco());
		restaurante.setTaxaEntrega(dto.taxaEntrega());
		Restaurante atualizado = restauranteRepository.save(restaurante);
		return restauranteMapper.toResponseDto(atualizado);
	}

	@Override
	public void deletar(Long id) {
		restauranteRepository.deleteById(id);
	}
}