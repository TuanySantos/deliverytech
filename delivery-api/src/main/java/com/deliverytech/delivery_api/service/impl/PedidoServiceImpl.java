package com.deliverytech.delivery_api.service.impl;
import java.math.BigDecimal;
import com.deliverytech.delivery_api.projection.FaturamentoPorCategoriaProjection;
import com.deliverytech.delivery_api.projection.VendasPorRestauranteProjection;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deliverytech.delivery_api.service.PedidoService;
import com.deliverytech.delivery_api.entity.Pedido;
import com.deliverytech.delivery_api.enums.StatusPedido;
import com.deliverytech.delivery_api.enums.ErroNegocio;
import com.deliverytech.delivery_api.exception.BusinessException;
import com.deliverytech.delivery_api.entity.Cliente;
import com.deliverytech.delivery_api.entity.Restaurante;
import com.deliverytech.delivery_api.entity.Produto;
import com.deliverytech.delivery_api.repository.ClienteRepository;
import com.deliverytech.delivery_api.repository.RestauranteRepository;
import com.deliverytech.delivery_api.repository.ProdutoRepository;
import com.deliverytech.delivery_api.repository.PedidoRepository;
import com.deliverytech.delivery_api.dto.requestDto.PedidoRequestDTO;
import com.deliverytech.delivery_api.dto.responseDto.PedidoResponseDTO;
import com.deliverytech.delivery_api.mapper.PedidoMapper;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
@Service
@Transactional
public class PedidoServiceImpl implements PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private PedidoMapper pedidoMapper;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private RestauranteRepository restauranteRepository;
    
	@Autowired
	private ProdutoRepository produtoRepository;

	@Override
    @Transactional(readOnly = true)
	public List<PedidoResponseDTO> buscarPorClienteId(Long clienteId) {
		return pedidoMapper.toResponseDtoList(pedidoRepository.findByClienteId(clienteId));
	}

	@Override
    @Transactional(readOnly = true)
	public List<PedidoResponseDTO> buscarPorStatus(StatusPedido status) {
		return pedidoMapper.toResponseDtoList(pedidoRepository.findByStatus(status));
	}

	@Override
    @Transactional(readOnly = true)
	public List<PedidoResponseDTO> buscarTop10Recentes() {
		return pedidoMapper.toResponseDtoList(pedidoRepository.findTop10ByOrderByDataPedidoDesc());
	}

	@Override
    @Transactional(readOnly = true)
	public List<PedidoResponseDTO> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
		return pedidoMapper.toResponseDtoList(pedidoRepository.findByDataPedidoBetween(inicio, fim));
	}

	@Override
	public PedidoResponseDTO salvar(PedidoRequestDTO dto) {
		// Validação do cliente
		Cliente cliente = clienteRepository.findById(dto.clienteId())
			.orElseThrow(() -> new BusinessException(ErroNegocio.CLIENTE_NAO_ENCONTRADO, "Cliente não encontrado"));
		if (!cliente.isAtivo() || cliente.getEndereco() == null || cliente.getEndereco().isEmpty()) {
			throw new BusinessException(ErroNegocio.PEDIDO_INVALIDO, "Cliente inativo ou endereço inválido");
		}

		// Validação do restaurante
		Restaurante restaurante = restauranteRepository.findById(dto.restauranteId())
			.orElseThrow(() -> new BusinessException(ErroNegocio.PEDIDO_INVALIDO, "Restaurante não encontrado"));
		if (!restaurante.isAtivo()) {
			throw new BusinessException(ErroNegocio.PEDIDO_INVALIDO, "Restaurante fechado");
		}
		// Adicione lógica para entrega na região se necessário

		// Validação dos produtos e estoque
		BigDecimal subtotal = BigDecimal.ZERO;
		for (var itemDto : dto.itens()) {
			Produto produto = produtoRepository.findById(itemDto.produtoId())
				.orElseThrow(() -> new BusinessException(ErroNegocio.PEDIDO_INVALIDO, "Produto não encontrado"));
			if (!produto.isDisponivel()) {
				throw new BusinessException(ErroNegocio.ESTOQUE_INSUFICIENTE, "Produto indisponível");
			}
			// Se houver campo de estoque, valide aqui
			// if (produto.getEstoque() < itemDto.quantidade()) {
			//     throw new BusinessException(ErroNegocio.ESTOQUE_INSUFICIENTE, "Estoque insuficiente");
			// }
			subtotal = subtotal.add(produto.getPreco().multiply(BigDecimal.valueOf(itemDto.quantidade())));
		}

		// Cálculo do pedido
		BigDecimal taxaEntrega = restaurante.getTaxaEntrega();
		BigDecimal desconto = BigDecimal.ZERO; // Adicione lógica de desconto se necessário
		BigDecimal total = subtotal.add(taxaEntrega).subtract(desconto);

		Pedido pedido = pedidoMapper.toEntity(dto);
		pedido.setValorTotal(total);
		pedido.setStatus(StatusPedido.PENDENTE);
		Pedido salvo = pedidoRepository.save(pedido);
		return pedidoMapper.toResponseDto(salvo);
	}

	@Override
    @Transactional(readOnly = true)
	public PedidoResponseDTO buscarPorId(Long id) {
		Pedido pedido = pedidoRepository.findById(id)
			.orElseThrow(() -> new BusinessException(ErroNegocio.PEDIDO_INVALIDO, "Pedido não encontrado"));
		return pedidoMapper.toResponseDto(pedido);
	}

	@Override
	public PedidoResponseDTO atualizar(Long id, PedidoRequestDTO dto) {
		Pedido pedido = pedidoRepository.findById(id)
			.orElseThrow(() -> new BusinessException(ErroNegocio.PEDIDO_INVALIDO, "Pedido não encontrado"));
		// Atualize os campos necessários do pedido
		Pedido atualizado = pedidoRepository.save(pedido);
		return pedidoMapper.toResponseDto(atualizado);
	}

	@Override
	public void deletar(Long id) {
		pedidoRepository.deleteById(id);
	}

    	// Relatório: Total de vendas por restaurante
    @Transactional(readOnly = true)
	public List<VendasPorRestauranteProjection> listarTotalVendasPorRestaurante() {
		return pedidoRepository.getTotalVendasPorRestaurante();
	}

	// Relatório: Faturamento por categoria
    @Transactional(readOnly = true)
	public List<FaturamentoPorCategoriaProjection> listarFaturamentoPorCategoria() {
		return pedidoRepository.getFaturamentoPorCategoria();
	}

	// Relatório: Pedidos com valor acima de X
    @Transactional(readOnly = true)
	public List<PedidoResponseDTO> listarPedidosComValorAcima(BigDecimal valor) {
		return pedidoMapper.toResponseDtoList(pedidoRepository.findPedidosComValorAcima(valor));
	}

	// Relatório: Por período e status
    @Transactional(readOnly = true)
	public List<PedidoResponseDTO> listarPorPeriodoEStatus(LocalDateTime inicio, LocalDateTime fim, StatusPedido status) {
		return pedidoMapper.toResponseDtoList(pedidoRepository.findByPeriodoAndStatus(inicio, fim, status));
	}
}