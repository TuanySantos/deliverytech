

package com.deliverytech.delivery_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import java.util.List;

import com.deliverytech.delivery_api.service.RestauranteService;
import com.deliverytech.delivery_api.dto.requestDto.RestauranteRequestDTO;
import com.deliverytech.delivery_api.dto.responseDto.RestauranteResponseDTO;

@RestController
@RequestMapping("/restaurantes")
public class RestaurantController {

    private final RestauranteService restauranteService;

    public RestaurantController(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }

    @GetMapping("/categoria/{categoria}")
    public List<RestauranteResponseDTO> buscarPorCategoria(@PathVariable String categoria) {
        return restauranteService.buscarPorCategoria(categoria);
    }

    @GetMapping("/ativos")
    public List<RestauranteResponseDTO> buscarAtivos() {
        return restauranteService.buscarAtivos();
    }

    @GetMapping("/taxa-entrega")
    public List<RestauranteResponseDTO> buscarPorTaxaEntregaMenorIgual(@RequestParam java.math.BigDecimal taxa) {
        return restauranteService.buscarPorTaxaEntregaMenorIgual(taxa);
    }

    @GetMapping("/top5")
    public List<RestauranteResponseDTO> buscarTop5PorNomeAsc() {
        return restauranteService.buscarTop5PorNomeAsc();
    }

    @GetMapping("/nome/{nome}")
    public List<RestauranteResponseDTO> buscarPorNome(@PathVariable String nome) {
        return restauranteService.buscarPorNome(nome);
    }

    // CRUD
    @PostMapping
    public ResponseEntity<RestauranteResponseDTO> criarRestaurante(@RequestBody RestauranteRequestDTO dto) {
        RestauranteResponseDTO salvo = restauranteService.salvar(dto);
        return ResponseEntity.ok(salvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestauranteResponseDTO> buscarPorId(@PathVariable Long id) {
        RestauranteResponseDTO restaurante = restauranteService.buscarPorId(id);
        return ResponseEntity.ok(restaurante);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestauranteResponseDTO> atualizarRestaurante(@PathVariable Long id, @RequestBody RestauranteRequestDTO dto) {
        RestauranteResponseDTO atualizado = restauranteService.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarRestaurante(@PathVariable Long id) {
        restauranteService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}