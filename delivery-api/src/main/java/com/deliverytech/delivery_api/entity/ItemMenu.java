package com.deliverytech.delivery_api.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "itemMenu")
public class ItemMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private boolean disponivel;

    @ManyToOne
    @JoinColumn(name = "restauranteId")
    private Restaurante restaurante;
    
}
