package com.deliverytech.delivery_api.repository;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.deliverytech.delivery_api.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByRestauranteId(Long restauranteId);
    List<Produto> findByDisponivelTrue();
    List<Produto> findByCategoria(String categoria);
    List<Produto> findByPrecoLessThanEqual(BigDecimal preco);
}