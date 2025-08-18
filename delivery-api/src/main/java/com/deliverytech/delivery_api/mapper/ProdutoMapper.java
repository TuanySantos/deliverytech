package com.deliverytech.delivery_api.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.deliverytech.delivery_api.dto.requestDto.ProdutoRequestDTO;
import com.deliverytech.delivery_api.dto.responseDto.ProdutoResponseDTO;
import com.deliverytech.delivery_api.entity.Produto;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {
    Produto toEntity(ProdutoRequestDTO dto);
    ProdutoResponseDTO toResponseDto(Produto entity);
    List<ProdutoResponseDTO> toResponseDtoList(List<Produto> entities);
}
