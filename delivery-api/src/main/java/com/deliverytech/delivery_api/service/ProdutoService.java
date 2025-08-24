package com.deliverytech.delivery_api.service;

import com.deliverytech.delivery_api.entity.Produto;
import java.math.BigDecimal;
import java.util.List;

public interface ProdutoService {
	List<Produto> buscarPorRestauranteId(Long restauranteId);
	List<Produto> buscarDisponiveis();
	List<Produto> buscarPorCategoria(String categoria);
	List<Produto> buscarPorPrecoMenorIgual(BigDecimal preco);
}
