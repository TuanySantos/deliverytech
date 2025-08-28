package com.deliverytech.delivery_api.controller;

import com.deliverytech.delivery_api.dto.requestDto.RestauranteRequestDTO;
import com.deliverytech.delivery_api.dto.responseDto.ProdutoResponseDTO;
import com.deliverytech.delivery_api.dto.responseDto.RestauranteResponseDTO;
import com.deliverytech.delivery_api.service.RestauranteService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/restaurantes")
public class RestauranteController {

    private final RestauranteService restauranteService;

    public RestauranteController(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }

    // POST /api/restaurantes
    @PostMapping
    public ResponseEntity<RestauranteResponseDTO> cadastrarRestaurante(@Valid @RequestBody RestauranteRequestDTO dto) {
        RestauranteResponseDTO response = restauranteService.cadastrarRestaurante(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // GET /api/restaurantes/{id}
    @GetMapping("/{id}")
    public ResponseEntity<RestauranteResponseDTO> buscarPorId(@PathVariable Long id) {
        RestauranteResponseDTO response = restauranteService.buscarRestaurantePorId(id);
        return ResponseEntity.ok(response);
    }

    // GET /api/restaurantes/categoria/{categoria}
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<RestauranteResponseDTO>> buscarPorCategoria(@PathVariable String categoria) {
        List<RestauranteResponseDTO> restaurantes = restauranteService.buscarRestaurantesPorCategoria(categoria);
        return ResponseEntity.ok(restaurantes);
    }

    // GET /api/restaurantes/disponiveis
    @GetMapping("/disponiveis")
    public ResponseEntity<List<RestauranteResponseDTO>> buscarRestaurantesDisponiveis() {
        List<RestauranteResponseDTO> restaurantes = restauranteService.buscarRestaurantesDisponiveis();
        return ResponseEntity.ok(restaurantes);
    }

    // PUT /api/restaurantes/{id}
    @PutMapping("/{id}")
    public ResponseEntity<RestauranteResponseDTO> atualizarRestaurante(@PathVariable Long id, @Valid @RequestBody RestauranteRequestDTO dto) {
        RestauranteResponseDTO response = restauranteService.atualizarRestaurante(id, dto);
        return ResponseEntity.ok(response);
    }
    
    // GET /api/restaurantes/{id}/taxa-entrega?cep=XXXXX
    @GetMapping("/{id}/taxa-entrega")
    public ResponseEntity<BigDecimal> calcularTaxaEntrega(@PathVariable Long id, @RequestParam String cep) {
        BigDecimal taxa = restauranteService.calcularTaxaEntrega(id, cep);
        return ResponseEntity.ok(taxa);
    }

    //GET /api/restaurantes/{id}/produtos
    @GetMapping("/{id}/produtos")
    public ResponseEntity<List<ProdutoResponseDTO>> listarProdutosDisponiveis(@PathVariable Long id) {
        List<ProdutoResponseDTO> produtos = restauranteService.listarProdutosDisponiveis(id);
        return ResponseEntity.ok(produtos);
    }
}
