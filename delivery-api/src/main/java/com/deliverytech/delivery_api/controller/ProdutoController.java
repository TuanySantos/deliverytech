
package com.deliverytech.delivery_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deliverytech.delivery_api.service.ProdutoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.math.BigDecimal;
import java.util.List;
import com.deliverytech.delivery_api.entity.Produto;

@RestController
@RequestMapping("/produtos")
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;

public class ProdutoController {

	private final ProdutoService produtoService;

	@Autowired
	public ProdutoController(ProdutoService produtoService) {
		this.produtoService = produtoService;
	}

	@GetMapping("/restaurante/{restauranteId}")
	public List<Produto> buscarPorRestauranteId(@PathVariable Long restauranteId) {
		return produtoService.buscarPorRestauranteId(restauranteId);
	}

	@GetMapping("/disponiveis")
	public List<Produto> buscarDisponiveis() {
		return produtoService.buscarDisponiveis();
	}

	@GetMapping("/categoria/{categoria}")
	public List<Produto> buscarPorCategoria(@PathVariable String categoria) {
		return produtoService.buscarPorCategoria(categoria);
	}

	@GetMapping("/preco")
	public List<Produto> buscarPorPrecoMenorIgual(@RequestParam BigDecimal preco) {
		return produtoService.buscarPorPrecoMenorIgual(preco);
	}

	// CRUD
	@PostMapping
	public ResponseEntity<Produto> criarProduto(@RequestBody Produto produto) {
		Produto salvo = produtoService.salvar(produto);
		return ResponseEntity.ok(salvo);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
		Produto produto = produtoService.buscarPorId(id);
		return ResponseEntity.ok(produto);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Produto> atualizarProduto(@PathVariable Long id, @RequestBody Produto produto) {
		Produto atualizado = produtoService.atualizar(id, produto);
		return ResponseEntity.ok(atualizado);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
		produtoService.deletar(id);
		return ResponseEntity.noContent().build();
	}
}
