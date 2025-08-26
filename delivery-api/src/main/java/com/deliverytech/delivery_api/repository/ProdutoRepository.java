package com.deliverytech.delivery_api.repository;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.deliverytech.delivery_api.projection.ProdutoMaisVendidoProjection;
import com.deliverytech.delivery_api.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByRestauranteId(Long restauranteId);
    List<Produto> findByDisponivelTrue();
    List<Produto> findByCategoria(String categoria);
    List<Produto> findByPrecoLessThanEqual(BigDecimal preco);
    List<Produto> findByRestauranteIdAndDisponivelTrue(Long restauranteId);
    List<Produto> findByCategoriaAndDisponivelTrue(String categoria);

    @Query(value = "SELECT pr.nome as nome, SUM(ip.quantidade) as quantidadeVendida FROM item_pedido ip JOIN Produto pr ON pr.id = ip.produto_id GROUP BY pr.nome ORDER BY quantidadeVendida DESC LIMIT 10", nativeQuery = true)
    List<ProdutoMaisVendidoProjection> findProdutosMaisVendidos();
}