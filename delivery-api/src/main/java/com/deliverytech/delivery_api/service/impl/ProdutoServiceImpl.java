package com.deliverytech.delivery_api.service.impl;
import com.deliverytech.delivery_api.projection.ProdutoMaisVendidoProjection;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deliverytech.delivery_api.service.ProdutoService;
import com.deliverytech.delivery_api.entity.Produto;
import com.deliverytech.delivery_api.repository.ProdutoRepository;
import com.deliverytech.delivery_api.dto.requestDto.ProdutoRequestDTO;
import com.deliverytech.delivery_api.dto.responseDto.ProdutoResponseDTO;
import com.deliverytech.delivery_api.mapper.ProdutoMapper;
import com.deliverytech.delivery_api.exception.BusinessException;
import com.deliverytech.delivery_api.enums.ErroNegocio;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;

@Service
@Transactional
public class ProdutoServiceImpl implements ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private ProdutoMapper produtoMapper;


	@Override
    @Transactional(readOnly = true)
	public List<ProdutoResponseDTO> buscarPorRestauranteId(Long restauranteId) {
		return produtoMapper.toResponseDtoList(produtoRepository.findByRestauranteId(restauranteId));
	}

	@Override
    @Transactional(readOnly = true)
	public List<ProdutoResponseDTO> buscarDisponiveis() {
		return produtoMapper.toResponseDtoList(produtoRepository.findByDisponivelTrue());
	}

	@Override
    @Transactional(readOnly = true)
	public List<ProdutoResponseDTO> buscarPorCategoria(String categoria) {
		return produtoMapper.toResponseDtoList(produtoRepository.findByCategoria(categoria));
	}

	@Override
    @Transactional(readOnly = true)
	public List<ProdutoResponseDTO> buscarPorPrecoMenorIgual(BigDecimal preco) {
		return produtoMapper.toResponseDtoList(produtoRepository.findByPrecoLessThanEqual(preco));
	}

	@Override
	public ProdutoResponseDTO salvar(ProdutoRequestDTO dto) {
	    if (dto.nome() == null || dto.nome().isBlank()) {
	        throw new ValidationException("Nome do produto é obrigatório");
	    }
		Produto produto = produtoMapper.toEntity(dto);
		Produto salvo = produtoRepository.save(produto);
		return produtoMapper.toResponseDto(salvo);
	}

	@Override
    @Transactional(readOnly = true)
	public ProdutoResponseDTO buscarPorId(Long id) {
		Produto produto = produtoRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));
		return produtoMapper.toResponseDto(produto);
	}

	@Override
	public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO dto) {
		Produto produto = produtoRepository.findById(id)
			.orElseThrow(() -> new BusinessException(ErroNegocio.PRODUTO_NAO_ENCONTRADO, "Produto não encontrado"));
		produto.setNome(dto.nome());
		produto.setDescricao(dto.descricao());
		produto.setPreco(dto.preco());
		produto.setCategoria(dto.categoria());
		Produto atualizado = produtoRepository.save(produto);
		return produtoMapper.toResponseDto(atualizado);
	}

	@Override
	public void deletar(Long id) {
		produtoRepository.deleteById(id);
	}

    	// Relatório: Produtos mais vendidos
    @Transactional(readOnly = true)
	public List<ProdutoMaisVendidoProjection> listarProdutosMaisVendidos() {
		return produtoRepository.findProdutosMaisVendidos();
	}
}