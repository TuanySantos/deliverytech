package com.deliverytech.delivery_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.deliverytech.delivery_api.dto.RestaurantDTO;
import com.deliverytech.delivery_api.entity.*;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    RestaurantMapper INSTANCE = Mappers.getMapper( RestaurantMapper.class); 
    
        @Mapping(source = "name", target = "restaurantName")
        @Mapping(source = "address", target = "address")
        RestaurantDTO restaurantToRestaurantDto(Restaurant restaurant);
}
