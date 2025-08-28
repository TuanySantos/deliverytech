package com.deliverytech.delivery_api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.deliverytech.delivery_api.service.RestauranteService;
import com.deliverytech.delivery_api.dto.responseDto.ProdutoResponseDTO;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RestauranteControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestauranteService restauranteService;

    @Test
    void deveRetornarListaDeProdutosDisponiveisPorRestaurante() throws Exception {
        List<ProdutoResponseDTO> produtos = List.of(
            new ProdutoResponseDTO(
            1L,
            "Pizza",
            "Pizza de muçarela",
            BigDecimal.valueOf(30.0),
            "Salgado",
            true,
            "Restaurante X"
            ),
            new ProdutoResponseDTO(
            2L,
            "Refrigerante",
            "Refrigerante Guaraná",
            BigDecimal.valueOf(10.0),
            "Bebida",
            true,
            "Restaurante Y"
        )
        );
        when(restauranteService.listarProdutosDisponiveis(1L)).thenReturn(produtos);

        mockMvc.perform(get("/api/restaurantes/1/produtos")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].nome").value("Pizza"))
            .andExpect(jsonPath("$[1].nome").value("Refrigerante"));
    }
}