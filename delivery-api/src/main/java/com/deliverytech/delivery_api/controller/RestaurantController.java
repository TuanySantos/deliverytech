package com.deliverytech.delivery_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deliverytech.delivery_api.service.RestauranteService;

@RestController
@RequestMapping("/api/v1/restaurantes")
public class RestaurantController {

    @Autowired
    private RestauranteService restaurantService;

}