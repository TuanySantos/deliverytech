package com.deliverytech.delivery_api.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deliverytech.delivery_api.service.RestauranteService;
import com.deliverytech.delivery_api.entity.Restaurante;
import com.deliverytech.delivery_api.repository.RestauranteRepository;
import com.deliverytech.delivery_api.dto.requestDto.RestauranteRequestDTO;
import com.deliverytech.delivery_api.dto.responseDto.RestauranteResponseDTO;
import com.deliverytech.delivery_api.mapper.RestauranteMapper;
import com.deliverytech.delivery_api.exception.BusinessException;
import com.deliverytech.delivery_api.enums.ErroNegocio;
import java.util.List;
import java.math.BigDecimal;
import jakarta.validation.ValidationException;

@Service
@Transactional
public class RestauranteServiceImpl implements RestauranteService {

	private final RestauranteRepository restauranteRepository;
	private final RestauranteMapper restauranteMapper;

	public RestauranteServiceImpl(RestauranteRepository restauranteRepository, RestauranteMapper restauranteMapper) {
		this.restauranteRepository = restauranteRepository;
		this.restauranteMapper = restauranteMapper;
	}

    @Override
    public RestauranteResponseDTO cadastrarRestaurante(RestauranteRequestDTO dto) {
        if (dto.nome() == null || dto.nome().isBlank()) {
            throw new ValidationException("Nome do restaurante é obrigatório");
        }
        if (dto.categoria() == null || dto.categoria().isBlank()) {
            throw new ValidationException("Categoria do restaurante é obrigatória");
        }
        if (dto.endereco() == null || dto.endereco().isBlank()) {
            throw new ValidationException("Endereço do restaurante é obrigatório");
        }
        if (dto.taxaEntrega() == null || dto.taxaEntrega().compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("Taxa de entrega deve ser zero ou positiva");
        }
        Restaurante restaurante = restauranteMapper.toEntity(dto);
        Restaurante salvo = restauranteRepository.save(restaurante);
        return restauranteMapper.toResponseDto(salvo);
    }

    @Override
    @Transactional(readOnly = true)
    public RestauranteResponseDTO buscarRestaurantePorId(Long id) {
        Restaurante restaurante = restauranteRepository.findById(id)
            .orElseThrow(() -> new BusinessException(ErroNegocio.RESTAURANTE_NAO_ENCONTRADO, "Restaurante não encontrado"));
        return restauranteMapper.toResponseDto(restaurante);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RestauranteResponseDTO> buscarRestaurantesPorCategoria(String categoria) {
        return restauranteMapper.toResponseDtoList(restauranteRepository.findByCategoria(categoria));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RestauranteResponseDTO> buscarRestaurantesDisponiveis() {
        return restauranteMapper.toResponseDtoList(restauranteRepository.findByAtivoTrue());
    }

    @Override
    public RestauranteResponseDTO atualizarRestaurante(Long id, RestauranteRequestDTO dto) {
        Restaurante restaurante = restauranteRepository.findById(id)
            .orElseThrow(() -> new BusinessException(ErroNegocio.RESTAURANTE_NAO_ENCONTRADO, "Restaurante não encontrado"));
        if (dto.nome() == null || dto.nome().isBlank()) {
            throw new BusinessException(ErroNegocio.PEDIDO_INVALIDO, "Nome do restaurante é obrigatório");
        }
        if (dto.categoria() == null || dto.categoria().isBlank()) {
            throw new BusinessException(ErroNegocio.PEDIDO_INVALIDO, "Categoria do restaurante é obrigatória");
        }
        if (dto.endereco() == null || dto.endereco().isBlank()) {
            throw new BusinessException(ErroNegocio.PEDIDO_INVALIDO, "Endereço do restaurante é obrigatório");
        }
        if (dto.taxaEntrega() == null || dto.taxaEntrega().compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException(ErroNegocio.PEDIDO_INVALIDO, "Taxa de entrega deve ser zero ou positiva");
        }
        restaurante.setNome(dto.nome());
        restaurante.setCategoria(dto.categoria());
        restaurante.setEndereco(dto.endereco());
        restaurante.setTaxaEntrega(dto.taxaEntrega());
        Restaurante atualizado = restauranteRepository.save(restaurante);
        return restauranteMapper.toResponseDto(atualizado);
    }

    @Override
    public BigDecimal calcularTaxaEntrega(Long restauranteId, String cep) {
        Restaurante restaurante = restauranteRepository.findById(restauranteId)
            .orElseThrow(() -> new BusinessException(ErroNegocio.RESTAURANTE_NAO_ENCONTRADO, "Restaurante não encontrado"));
		//TODO: Implementar lógica baseada no CEP
        return restaurante.getTaxaEntrega();
    }
}