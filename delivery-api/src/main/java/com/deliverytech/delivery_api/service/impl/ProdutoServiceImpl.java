package com.deliverytech.delivery_api.service.impl;

import org.springframework.stereotype.Service;
import com.deliverytech.delivery_api.service.ProdutoService;
import com.deliverytech.delivery_api.entity.Produto;
import com.deliverytech.delivery_api.repository.ProdutoRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ProdutoServiceImpl implements ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Override
	public List<Produto> buscarPorRestauranteId(Long restauranteId) {
		return produtoRepository.findByRestauranteId(restauranteId);
	}

	@Override
	public List<Produto> buscarDisponiveis() {
		return produtoRepository.findByDisponivelTrue();
	}

	@Override
	public List<Produto> buscarPorCategoria(String categoria) {
		return produtoRepository.findByCategoria(categoria);
	}

	@Override
	public List<Produto> buscarPorPrecoMenorIgual(BigDecimal preco) {
		return produtoRepository.findByPrecoLessThanEqual(preco);
	}
}