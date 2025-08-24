package com.deliverytech.delivery_api.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.deliverytech.delivery_api.projection.RankingClienteProjection;
import com.deliverytech.delivery_api.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByEmail(String email);
    List<Cliente> findByAtivoTrue();
    Optional<Cliente> findByNomeContainingIgnoreCase(String nome);
    boolean existsByEmail(String email);

    @Query(value = "SELECT c.nome as nome, COUNT(p.id) as totalPedidos FROM Cliente c JOIN pedido p ON c.id = p.cliente_id GROUP BY c.nome ORDER BY totalPedidos DESC LIMIT 10", nativeQuery = true)
    List<RankingClienteProjection> findRankingClientesPorPedidos();
}
