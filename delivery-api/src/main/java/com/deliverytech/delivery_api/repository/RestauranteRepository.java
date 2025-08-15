package com.deliverytech.delivery_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deliverytech.delivery_api.entity.Restaurante;
import java.util.List;


@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
    List<Restaurante> findByCategoria(String categoria);
    List<Restaurante> findByAtivo(boolean ativo);
    List<Restaurante> findByTaxaEntregaLessThanEqual(double taxaEntrega);
    List<Restaurante> findTop5ByOrderByNomeAsc();
}
