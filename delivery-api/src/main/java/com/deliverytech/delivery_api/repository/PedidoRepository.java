package com.deliverytech.delivery_api.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deliverytech.delivery_api.entity.Pedido;
import com.deliverytech.delivery_api.enums.StatusPedidoEnum;
import com.deliverytech.delivery_api.projection.FaturamentoPorCategoriaProjection;
import com.deliverytech.delivery_api.projection.VendasPorRestauranteProjection;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByClienteId(Long clienteId);
    List<Pedido> findByStatus(StatusPedidoEnum status);
    List<Pedido> findTop10ByOrderByDataPedidoDesc();
    List<Pedido> findByDataPedidoBetween(LocalDateTime inicio, LocalDateTime fim);

    // Total de vendas por restaurante
    @Query(value = "SELECT r.nome as restaurante, SUM(p.valor_total) as totalVendas FROM pedido p JOIN restaurante r ON r.id = p.restaurant_id GROUP BY r.nome", nativeQuery = true)
    List<VendasPorRestauranteProjection> getTotalVendasPorRestaurante();

    // Pedidos com valor acima de X
    @Query("SELECT p FROM Pedido p WHERE p.valorTotal > :valor")
    List<Pedido> findPedidosComValorAcima(@Param("valor") BigDecimal valor);

    // Relatório por período e status
    @Query("SELECT p FROM Pedido p WHERE p.dataPedido BETWEEN :inicio AND :fim AND p.status = :status")
    List<Pedido> findByPeriodoAndStatus(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim, @Param("status") StatusPedidoEnum status);

    // Faturamento por categoria (nativo)
    @Query(value = "SELECT pr.categoria as categoria, SUM(p.valor_total) as faturamento FROM Pedido p JOIN produto pr ON pr.id = p.produto_id GROUP BY pr.categoria", nativeQuery = true)
    List<FaturamentoPorCategoriaProjection> getFaturamentoPorCategoria();
}
