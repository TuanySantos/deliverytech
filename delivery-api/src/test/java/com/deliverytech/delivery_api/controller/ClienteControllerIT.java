package com.deliverytech.delivery_api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.deliverytech.delivery_api.dto.responseDto.ClienteResponseDTO;
import com.deliverytech.delivery_api.service.ClienteService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ClienteControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Test
    void deveRetornarStatus201AoCadastrarCliente() throws Exception {
     
        when(clienteService.cadastrarCliente(any())).thenReturn(
                new ClienteResponseDTO(
                    1L,"João Silva","joao@email.com", "Rua A, 123","11999999999",true)
                    );
        mockMvc.perform(post("/api/clientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                    "nome": "João Silva",
                    "email": "joao@email.com",
                    "senha": "senha123",
                    "endereco": "Rua A, 123",
                    "telefone": "11999999999"
                }
            """))
            .andExpect(status().isCreated());
    }
}