package com.deliverytech.delivery_api.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.deliverytech.delivery_api.entity.Pedido;
import com.deliverytech.delivery_api.enums.StatusPedidoEnum;


public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByClienteId(Long clienteId);
    List<Pedido> findByStatus(StatusPedidoEnum status);
    List<Pedido> findTop10ByOrderByDataPedidoDesc();
    List<Pedido> findByDataPedidoBetween(LocalDateTime inicio, LocalDateTime fim);

}
