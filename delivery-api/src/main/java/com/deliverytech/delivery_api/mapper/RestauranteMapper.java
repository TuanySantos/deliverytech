package com.deliverytech.delivery_api.mapper;

import org.mapstruct.Mapper;
import com.deliverytech.delivery_api.dto.RestauranteDTO;
import com.deliverytech.delivery_api.entity.Restaurante;

@Mapper(componentModel = "spring")
public interface RestauranteMapper {

        RestauranteDTO restaurantToRestaurantDto(Restaurante restaurant);
}
