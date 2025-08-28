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

import com.deliverytech.delivery_api.dto.responseDto.PedidoResponseDTO;
import com.deliverytech.delivery_api.dto.responseDto.ItemPedidoResponseDTO;
import com.deliverytech.delivery_api.service.PedidoService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PedidoControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PedidoService pedidoService;

     @Test
    void deveCriarPedidoComSucesso() throws Exception {
        // Arrange
        PedidoResponseDTO responseMock = new PedidoResponseDTO(
            1L,
            "João Silva",
            "Restaurante A",
            LocalDateTime.now(),
            List.of(
                new ItemPedidoResponseDTO("Hambúrguer", 2, new BigDecimal("31.90")),
                new ItemPedidoResponseDTO("Batata Frita", 1, new BigDecimal("14.00"))
            ),
            30
        );

        when(pedidoService.criarPedido(any())).thenReturn(responseMock);

        // Act & Assert
        mockMvc.perform(post("/api/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "clienteId": 1,
                      "restauranteId": 1,
                      "enderecoEntrega": "Rua B, 456",
                      "itens": [
                        {"produtoId": 1, "quantidade": 2},
                        {"produtoId": 2, "quantidade": 1}
                      ]
                    }
                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.clienteNome").value("João Silva"))
                .andExpect(jsonPath("$.restauranteNome").value("Restaurante A"))
                .andExpect(jsonPath("$.tempoEstimadoEntrega").value(30))
                .andExpect(jsonPath("$.itens.length()").value(2))
                .andExpect(jsonPath("$.itens[0].produtoNome").value("Hambúrguer"))
                .andExpect(jsonPath("$.itens[0].quantidade").value(2))
                .andExpect(jsonPath("$.itens[0].precoTotal").value(31.90))
                .andExpect(jsonPath("$.itens[1].produtoNome").value("Batata Frita"))
                .andExpect(jsonPath("$.itens[1].quantidade").value(1))
                .andExpect(jsonPath("$.itens[1].precoTotal").value(14.00));
    }

    @Test
    void deveRetornarBadRequestQuandoDadosInvalidos() throws Exception {
        // Act & Assert - Teste com dados inválidos
        mockMvc.perform(post("/api/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "clienteId": null,
                      "restauranteId": 1,
                      "enderecoEntrega": "",
                      "itens": []
                    }
                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornarBadRequestQuandoListaItensVazia() throws Exception {
        // Act & Assert - Teste com lista de itens vazia
        mockMvc.perform(post("/api/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "clienteId": 1,
                      "restauranteId": 1,
                      "enderecoEntrega": "Rua B, 456",
                      "itens": []
                    }
                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornarBadRequestQuandoQuantidadeInvalida() throws Exception {
        // Act & Assert - Teste com quantidade inválida
        mockMvc.perform(post("/api/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "clienteId": 1,
                      "restauranteId": 1,
                      "enderecoEntrega": "Rua B, 456",
                      "itens": [
                        {"produtoId": 1, "quantidade": 0},
                        {"produtoId": 2, "quantidade": -1}
                      ]
                    }
                """))
                .andExpect(status().isBadRequest());
    }
}