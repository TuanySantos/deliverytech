package com.deliverytech.delivery_api.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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
    private double taxaEntrega;

    @OneToMany(mappedBy = "restaurante")
    private List<ItemMenu> menu;

    @OneToMany(mappedBy = "restaurante")
    private List<Pedido> pedidos;
}
