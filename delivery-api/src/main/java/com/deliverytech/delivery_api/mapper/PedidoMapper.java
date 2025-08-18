package com.deliverytech.delivery_api.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.deliverytech.delivery_api.dto.requestDto.PedidoRequestDTO;
import com.deliverytech.delivery_api.dto.responseDto.PedidoResponseDTO;
import com.deliverytech.delivery_api.entity.Pedido;

@Mapper(componentModel = "spring")
public interface PedidoMapper {
    Pedido toEntity(PedidoRequestDTO dto);
    PedidoResponseDTO toResponseDto(Pedido entity);
    List<PedidoResponseDTO> toResponseDtoList(List<Pedido> entities);
}