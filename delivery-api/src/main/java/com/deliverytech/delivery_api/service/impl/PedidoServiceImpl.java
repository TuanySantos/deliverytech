package com.deliverytech.delivery_api.service.impl;
import java.math.BigDecimal;
import com.deliverytech.delivery_api.projection.FaturamentoPorCategoriaProjection;
import com.deliverytech.delivery_api.projection.VendasPorRestauranteProjection;
import org.springframework.stereotype.Service;
import com.deliverytech.delivery_api.service.PedidoService;
import com.deliverytech.delivery_api.entity.Pedido;
import com.deliverytech.delivery_api.enums.StatusPedidoEnum;
import com.deliverytech.delivery_api.repository.PedidoRepository;
import com.deliverytech.delivery_api.dto.requestDto.PedidoRequestDTO;
import com.deliverytech.delivery_api.dto.responseDto.PedidoResponseDTO;
import com.deliverytech.delivery_api.mapper.PedidoMapper;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
@Service
public class PedidoServiceImpl implements PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private PedidoMapper pedidoMapper;

	@Override
	public List<PedidoResponseDTO> buscarPorClienteId(Long clienteId) {
		return pedidoMapper.toResponseDtoList(pedidoRepository.findByClienteId(clienteId));
	}

	@Override
	public List<PedidoResponseDTO> buscarPorStatus(StatusPedidoEnum status) {
		return pedidoMapper.toResponseDtoList(pedidoRepository.findByStatus(status));
	}

	@Override
	public List<PedidoResponseDTO> buscarTop10Recentes() {
		return pedidoMapper.toResponseDtoList(pedidoRepository.findTop10ByOrderByDataPedidoDesc());
	}

	@Override
	public List<PedidoResponseDTO> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
		return pedidoMapper.toResponseDtoList(pedidoRepository.findByDataPedidoBetween(inicio, fim));
	}

	@Override
	public PedidoResponseDTO salvar(PedidoRequestDTO dto) {
		Pedido pedido = pedidoMapper.toEntity(dto);
		Pedido salvo = pedidoRepository.save(pedido);
		return pedidoMapper.toResponseDto(salvo);
	}

	@Override
	public PedidoResponseDTO buscarPorId(Long id) {
		Pedido pedido = pedidoRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
		return pedidoMapper.toResponseDto(pedido);
	}

	@Override
	public PedidoResponseDTO atualizar(Long id, PedidoRequestDTO dto) {
		Pedido pedido = pedidoRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
		// Atualize os campos necessários do pedido
		Pedido atualizado = pedidoRepository.save(pedido);
		return pedidoMapper.toResponseDto(atualizado);
	}

	@Override
	public void deletar(Long id) {
		pedidoRepository.deleteById(id);
	}

    	// Relatório: Total de vendas por restaurante
	public List<VendasPorRestauranteProjection> listarTotalVendasPorRestaurante() {
		return pedidoRepository.getTotalVendasPorRestaurante();
	}

	// Relatório: Faturamento por categoria
	public List<FaturamentoPorCategoriaProjection> listarFaturamentoPorCategoria() {
		return pedidoRepository.getFaturamentoPorCategoria();
	}

	// Relatório: Pedidos com valor acima de X
	public List<PedidoResponseDTO> listarPedidosComValorAcima(BigDecimal valor) {
		return pedidoMapper.toResponseDtoList(pedidoRepository.findPedidosComValorAcima(valor));
	}

	// Relatório: Por período e status
	public List<PedidoResponseDTO> listarPorPeriodoEStatus(LocalDateTime inicio, LocalDateTime fim, StatusPedidoEnum status) {
		return pedidoMapper.toResponseDtoList(pedidoRepository.findByPeriodoAndStatus(inicio, fim, status));
	}
}