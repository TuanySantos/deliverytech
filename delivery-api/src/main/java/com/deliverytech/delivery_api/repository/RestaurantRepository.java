package com.deliverytech.delivery_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deliverytech.delivery_api.entity.Restaurante;

public interface RestaurantRepository extends JpaRepository<Restaurante, Long> {
}
