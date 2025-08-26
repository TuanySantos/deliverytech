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
import com.deliverytech.delivery_api.dto.requestDto.ItemPedidoRequestDTO;
import com.deliverytech.delivery_api.mapper.PedidoMapper;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
@Transactional
public class PedidoServiceImpl implements PedidoService {

    private static final Logger log = LoggerFactory.getLogger(PedidoServiceImpl.class);

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
    public PedidoResponseDTO criarPedido(PedidoRequestDTO dto) {
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
        // TODO lógica para entrega na região
       
        if (cliente.getEndereco() == null || restaurante.getEndereco() == null ||
            !cliente.getEndereco().toLowerCase().contains(restaurante.getEndereco().toLowerCase())) {
            throw new BusinessException(ErroNegocio.PEDIDO_INVALIDO, "Restaurante não entrega na região do cliente");
        }

        // Validação dos produtos e estoque
        BigDecimal subtotal = BigDecimal.ZERO;
        for (var itemDto : dto.itens()) {
            Produto produto = produtoRepository.findById(itemDto.produtoId())
                .orElseThrow(() -> new BusinessException(ErroNegocio.PEDIDO_INVALIDO, "Produto não encontrado"));
            if (!produto.isDisponivel()) {
                throw new BusinessException(ErroNegocio.ESTOQUE_INSUFICIENTE, "Produto indisponível");
            }
            if (produto.getEstoque() < itemDto.quantidade()) {
                throw new BusinessException(ErroNegocio.ESTOQUE_INSUFICIENTE, "Estoque insuficiente");
            }
            subtotal = subtotal.add(produto.getPreco().multiply(BigDecimal.valueOf(itemDto.quantidade())));
        }

		// Cálculo do pedido
		BigDecimal taxaEntrega = restaurante.getTaxaEntrega();
		BigDecimal desconto = BigDecimal.ZERO;
		BigDecimal total = subtotal.add(taxaEntrega).subtract(desconto);

		Pedido pedido = pedidoMapper.toEntity(dto);
		pedido.setValorTotal(total);
		pedido.setStatus(StatusPedido.PENDENTE);
		// TODO: Cálculo do tempo estimado de entrega 
        int tempoEstimadoEntrega = 40;
        pedido.setTempoEstimadoEntrega(tempoEstimadoEntrega);
		Pedido salvo = pedidoRepository.save(pedido);

		for (var itemDto : dto.itens()) {
            Produto produto = produtoRepository.findById(itemDto.produtoId()).get();
            produto.setEstoque(produto.getEstoque() - itemDto.quantidade());
            produtoRepository.save(produto);
        }

		log.info("Novo pedido recebido para restaurante: {}", restaurante.getNome());

		return pedidoMapper.toResponseDto(salvo);
	}

    @Override
    @Transactional(readOnly = true)
    public PedidoResponseDTO buscarPedidoPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
            .orElseThrow(() -> new BusinessException(ErroNegocio.PEDIDO_INVALIDO, "Pedido não encontrado"));
        return pedidoMapper.toResponseDto(pedido);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> buscarPedidosPorCliente(Long clienteId) {
        return pedidoMapper.toResponseDtoList(pedidoRepository.findByClienteId(clienteId));
    }

    @Override
    public PedidoResponseDTO atualizarStatusPedido(Long id, StatusPedido status) {
        Pedido pedido = pedidoRepository.findById(id)
            .orElseThrow(() -> new BusinessException(ErroNegocio.PEDIDO_INVALIDO, "Pedido não encontrado"));
        // Validação de transição de status
        if (!isTransicaoValida(pedido.getStatus(), status)) {
            throw new BusinessException(ErroNegocio.PEDIDO_INVALIDO, "Transição de status inválida");
        }
        pedido.setStatus(status);
        Pedido atualizado = pedidoRepository.save(pedido);
        return pedidoMapper.toResponseDto(atualizado);
    }

    private boolean isTransicaoValida(StatusPedido atual, StatusPedido novo) {
        // Exemplo de validação simples
        if (atual == StatusPedido.CANCELADO) return false;
        if (atual == StatusPedido.ENTREGUE && novo != StatusPedido.ENTREGUE) return false;
        return true;
    }

    @Override
    public BigDecimal calcularTotalPedido(List<ItemPedidoRequestDTO> itens) {
        BigDecimal total = BigDecimal.ZERO;
        for (ItemPedidoRequestDTO itemDto : itens) {
            Produto produto = produtoRepository.findById(itemDto.produtoId())
                .orElseThrow(() -> new BusinessException(ErroNegocio.PEDIDO_INVALIDO, "Produto não encontrado"));
            if (!produto.isDisponivel()) {
                throw new BusinessException(ErroNegocio.ESTOQUE_INSUFICIENTE, "Produto indisponível");
            }
            if (produto.getEstoque() < itemDto.quantidade()) {
                throw new BusinessException(ErroNegocio.ESTOQUE_INSUFICIENTE, "Estoque insuficiente");
            }
            total = total.add(produto.getPreco().multiply(BigDecimal.valueOf(itemDto.quantidade())));
        }
        return total;
    }

    @Override
    public void cancelarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
            .orElseThrow(() -> new BusinessException(ErroNegocio.PEDIDO_INVALIDO, "Pedido não encontrado"));
        if (pedido.getStatus() != StatusPedido.PENDENTE && pedido.getStatus() != StatusPedido.PREPARANDO) {
            throw new BusinessException(ErroNegocio.PEDIDO_INVALIDO, "Pedido não pode ser cancelado neste status");
        }
        pedido.setStatus(StatusPedido.CANCELADO);
        pedidoRepository.save(pedido);
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