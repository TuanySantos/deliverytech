package com.deliverytech.delivery_api.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.deliverytech.delivery_api.enums.StatusPedidoEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Entity(name = "pedido")
public class Pedido {

    private LocalDateTime inicio;
    private LocalDateTime fim;
    private LocalDateTime dataPedido;
    private BigDecimal valorTotal;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "clienteId")
    private Cliente cliente;

    @Enumerated(EnumType.STRING)
    private StatusPedidoEnum status;

    @ManyToOne
    @JoinColumn(name = "restaurantId")
    private Restaurante restaurante;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itens;

    public void addItem(ItemPedido item) {
        if (itens == null) {
            itens = new ArrayList<>();
        }
        itens.add(item);
        item.setPedido(this);
    }

    public Pedido(Cliente cliente, Restaurante restaurante, LocalDateTime inicio) {
        this.cliente = cliente;
        this.restaurante = restaurante;
        this.inicio = inicio;
        this.status = StatusPedidoEnum.PENDENTE;
        this.itens = new ArrayList<>();
    }
}
