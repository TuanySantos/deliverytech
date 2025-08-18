package com.deliverytech.delivery_api.mapper;
import java.util.List;
import org.mapstruct.Mapper;
import com.deliverytech.delivery_api.dto.requestDto.RestauranteRequestDTO;
import com.deliverytech.delivery_api.dto.responseDto.RestauranteResponseDTO;
import com.deliverytech.delivery_api.entity.Restaurante;

@Mapper(componentModel = "spring")
public interface RestauranteMapper {
    Restaurante toEntity(RestauranteRequestDTO dto);
    RestauranteResponseDTO toResponseDto(Restaurante entity);
    List<RestauranteResponseDTO> toResponseDtoList(List<Restaurante> entities);
}