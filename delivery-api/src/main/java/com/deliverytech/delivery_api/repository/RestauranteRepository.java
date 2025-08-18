package com.deliverytech.delivery_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.deliverytech.delivery_api.entity.Restaurante;
import java.math.BigDecimal;
import java.util.List;


public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
    List<Restaurante> findByCategoria(String categoria);
    List<Restaurante> findByAtivoTrue();
    List<Restaurante> findByTaxaEntregaLessThanEqual(BigDecimal taxaEntrega);
    List<Restaurante> findTop5ByOrderByNomeAsc();
    List<Restaurante> findByNome(String nome);
}
