package com.deliverytech.delivery_api.service.impl;

import org.springframework.stereotype.Service;
import com.deliverytech.delivery_api.service.ProdutoService;
import com.deliverytech.delivery_api.entity.Produto;
import com.deliverytech.delivery_api.repository.ProdutoRepository;
import com.deliverytech.delivery_api.dto.requestDto.ProdutoRequestDTO;
import com.deliverytech.delivery_api.dto.responseDto.ProdutoResponseDTO;
import com.deliverytech.delivery_api.mapper.ProdutoMapper;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ProdutoServiceImpl implements ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private ProdutoMapper produtoMapper;

	@Override
	public List<ProdutoResponseDTO> buscarPorRestauranteId(Long restauranteId) {
		return produtoMapper.toResponseDtoList(produtoRepository.findByRestauranteId(restauranteId));
	}

	@Override
	public List<ProdutoResponseDTO> buscarDisponiveis() {
		return produtoMapper.toResponseDtoList(produtoRepository.findByDisponivelTrue());
	}

	@Override
	public List<ProdutoResponseDTO> buscarPorCategoria(String categoria) {
		return produtoMapper.toResponseDtoList(produtoRepository.findByCategoria(categoria));
	}

	@Override
	public List<ProdutoResponseDTO> buscarPorPrecoMenorIgual(BigDecimal preco) {
		return produtoMapper.toResponseDtoList(produtoRepository.findByPrecoLessThanEqual(preco));
	}

	@Override
	public ProdutoResponseDTO salvar(ProdutoRequestDTO dto) {
		Produto produto = produtoMapper.toEntity(dto);
		Produto salvo = produtoRepository.save(produto);
		return produtoMapper.toResponseDto(salvo);
	}

	@Override
	public ProdutoResponseDTO buscarPorId(Long id) {
		Produto produto = produtoRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("Produto não encontrado"));
		return produtoMapper.toResponseDto(produto);
	}

	@Override
	public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO dto) {
		Produto produto = produtoRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("Produto não encontrado"));
		produto.setNome(dto.nome());
		produto.setDescricao(dto.descricao());
		produto.setPreco(dto.preco());
		produto.setCategoria(dto.categoria());
		// restauranteId pode ser atualizado se necessário
		Produto atualizado = produtoRepository.save(produto);
		return produtoMapper.toResponseDto(atualizado);
	}

	@Override
	public void deletar(Long id) {
		produtoRepository.deleteById(id);
	}
}