package com.deliverytech.delivery_api.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.deliverytech.delivery_api.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByEmail(String email);
    Optional<Cliente> findByAtivo(boolean ativo);
    Optional<Cliente> findByNomeContainingIgnoreCase(String nome);
    Optional<Cliente> existsByEmail(String email);
}
