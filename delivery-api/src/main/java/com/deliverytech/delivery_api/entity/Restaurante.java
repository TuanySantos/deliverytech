package com.deliverytech.delivery_api.entity;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Entity(name = "restaurante")
public class Restaurante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String endereco;
    private String telefone;
    private String categoria;
    private boolean ativo;
    private BigDecimal taxaEntrega;

    @OneToMany(mappedBy = "restaurante")
    private List<Produto> menu;

    @OneToMany(mappedBy = "restaurante")
    private List<Pedido> pedidos;

    public Restaurante(Long id, String nome, String categoria, String endereco, boolean ativo, BigDecimal taxaEntrega) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.endereco = endereco;
        this.ativo = ativo;
        this.taxaEntrega = taxaEntrega;
    }
}
