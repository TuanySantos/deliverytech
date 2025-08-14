package com.deliverytech.delivery_api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deliverytech.delivery_api.dto.RestauranteDTO;
import com.deliverytech.delivery_api.entity.Restaurante;
import com.deliverytech.delivery_api.mapper.RestauranteMapper;
import com.deliverytech.delivery_api.repository.RestaurantRepository;


@Service
public class RestaurantService {

    @Autowired
    private final RestaurantRepository repository;

    @Autowired
    private final RestauranteMapper mapper;

    public RestaurantService(RestaurantRepository repository, RestauranteMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<RestauranteDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::restaurantToRestaurantDto)
                .collect(Collectors.toList());
    }

    public Restaurante save(RestauranteDTO dto) {
        return  mapper.restauranteDtoToRestaurante(dto);
    }

}