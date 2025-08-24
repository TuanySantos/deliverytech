

package com.deliverytech.delivery_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

import com.deliverytech.delivery_api.service.RestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestaurantController {

    private final RestauranteService restauranteService;

    @Autowired
    public RestaurantController(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }

    @GetMapping("/categoria/{categoria}")
    public List<com.deliverytech.delivery_api.entity.Restaurante> buscarPorCategoria(@PathVariable String categoria) {
        return restauranteService.buscarPorCategoria(categoria);
    }

    @GetMapping("/ativos")
    public List<com.deliverytech.delivery_api.entity.Restaurante> buscarAtivos() {
        return restauranteService.buscarAtivos();
    }

    @GetMapping("/taxa-entrega")
    public List<com.deliverytech.delivery_api.entity.Restaurante> buscarPorTaxaEntregaMenorIgual(@RequestParam java.math.BigDecimal taxa) {
        return restauranteService.buscarPorTaxaEntregaMenorIgual(taxa);
    }

    @GetMapping("/top5")
    public List<com.deliverytech.delivery_api.entity.Restaurante> buscarTop5PorNomeAsc() {
        return restauranteService.buscarTop5PorNomeAsc();
    }

    @GetMapping("/nome/{nome}")
    public List<com.deliverytech.delivery_api.entity.Restaurante> buscarPorNome(@PathVariable String nome) {
        return restauranteService.buscarPorNome(nome);
    }
}