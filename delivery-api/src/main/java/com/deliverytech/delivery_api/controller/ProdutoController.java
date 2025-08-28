package com.deliverytech.delivery_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deliverytech.delivery_api.service.ProdutoService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.math.BigDecimal;
import java.util.List;
import com.deliverytech.delivery_api.dto.requestDto.ProdutoRequestDTO;
import com.deliverytech.delivery_api.dto.responseDto.ProdutoResponseDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

	private final ProdutoService produtoService;

	public ProdutoController(ProdutoService produtoService) {
		this.produtoService = produtoService;
	}

	@GetMapping("/restaurante/{restauranteId}")
	public List<ProdutoResponseDTO> buscarPorRestauranteId(@PathVariable Long restauranteId) {
		return produtoService.buscarPorRestauranteId(restauranteId);
	}

	@GetMapping("/disponiveis")
	public List<ProdutoResponseDTO> buscarDisponiveis() {
		return produtoService.buscarDisponiveis();
	}

	@GetMapping("/categoria/{categoria}")
	public List<ProdutoResponseDTO> buscarPorCategoria(@PathVariable String categoria) {
		return produtoService.buscarPorCategoria(categoria);
	}

	@GetMapping("/preco")
	public List<ProdutoResponseDTO> buscarPorPrecoMenorIgual(@RequestParam BigDecimal preco) {
		return produtoService.buscarPorPrecoMenorIgual(preco);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProdutoResponseDTO> atualizarProduto(@PathVariable Long id, @Valid @RequestBody ProdutoRequestDTO dto) {
		ProdutoResponseDTO atualizado = produtoService.atualizar(id, dto);
		return ResponseEntity.ok(atualizado);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
		produtoService.deletar(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping
	public ResponseEntity<ProdutoResponseDTO> cadastrarProduto(@Valid @RequestBody ProdutoRequestDTO dto) {
		ProdutoResponseDTO produto = produtoService.cadastrarProduto(dto);
		return ResponseEntity.ok(produto);
	}
}
