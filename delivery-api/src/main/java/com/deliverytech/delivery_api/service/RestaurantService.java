package com.deliverytech.delivery_api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deliverytech.delivery_api.dto.RestaurantDTO;
import com.deliverytech.delivery_api.mapper.RestaurantMapper;
import com.deliverytech.delivery_api.repository.RestaurantRepository;


@Service
public class RestaurantService {


    private final RestaurantRepository repository;
    private final RestaurantMapper mapper;

    public RestaurantService(RestaurantRepository repository, RestaurantMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<RestaurantDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::restaurantToRestaurantDto)
                .collect(Collectors.toList());
    }

}