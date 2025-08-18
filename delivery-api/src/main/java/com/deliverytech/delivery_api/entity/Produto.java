package com.deliverytech.delivery_api.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "produto")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private boolean disponivel;
    private String categoria;

    @ManyToOne
    @JoinColumn(name = "restauranteId")
    private Restaurante restaurante;

    public Produto(Long id, String nome, String descricao, BigDecimal preco, String categoria, boolean disponivel, Restaurante restaurante) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.categoria = categoria;
        this.disponivel = disponivel;
        this.restaurante = restaurante;
    }
}
