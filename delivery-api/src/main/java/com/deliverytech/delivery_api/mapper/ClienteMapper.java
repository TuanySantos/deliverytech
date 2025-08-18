package com.deliverytech.delivery_api.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.deliverytech.delivery_api.dto.requestDto.ClienteRequestDTO;
import com.deliverytech.delivery_api.dto.responseDto.ClienteResponseDTO;
import com.deliverytech.delivery_api.entity.Cliente;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    Cliente toEntity(ClienteRequestDTO dto);
    ClienteResponseDTO toResponseDto(Cliente cliente);
    void updateEntityFromDto(ClienteRequestDTO dto, @MappingTarget Cliente cliente);
}
