package com.deliverytech.delivery_api.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.deliverytech.delivery_api.dto.requestDto.ItemPedidoRequestDTO;
import com.deliverytech.delivery_api.dto.responseDto.ItemPedidoResponseDTO;
import com.deliverytech.delivery_api.entity.ItemPedido;

@Mapper(componentModel = "spring")
public interface ItemPedidoMapper {
    ItemPedido toEntity(ItemPedidoRequestDTO dto);
    ItemPedidoResponseDTO toResponseDto(ItemPedido entity);
    List<ItemPedidoResponseDTO> toResponseDtoList(List<ItemPedido> entities);
}
