package com.deliverytech.delivery_api.service.impl;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.deliverytech.delivery_api.dto.requestDto.ClienteRequestDTO;
import com.deliverytech.delivery_api.dto.responseDto.ClienteResponseDTO;
import com.deliverytech.delivery_api.entity.Cliente;
import com.deliverytech.delivery_api.mapper.ClienteMapper;
import com.deliverytech.delivery_api.repository.ClienteRepository;
import com.deliverytech.delivery_api.service.ClienteService;
import com.deliverytech.delivery_api.projection.RankingClienteProjection;
import com.deliverytech.delivery_api.exception.BusinessException;
import com.deliverytech.delivery_api.enums.ErroNegocio;

@Service
@Transactional
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteMapper clienteMapper;

    @Override
    public ClienteResponseDTO salvar(ClienteRequestDTO dto) {
        Cliente cliente = clienteMapper.toEntity(dto);
        Cliente salvo = clienteRepository.save(cliente);
        return clienteMapper.toResponseDto(salvo);
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponseDTO buscarPorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErroNegocio.CLIENTE_NAO_ENCONTRADO, "Cliente não encontrado"));
        return clienteMapper.toResponseDto(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> listarTodos() {
        return clienteRepository.findAll()
                .stream()
                .map(clienteMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public ClienteResponseDTO atualizar(Long id, ClienteRequestDTO dto) {
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErroNegocio.CLIENTE_NAO_ENCONTRADO, "Cliente não encontrado"));

        clienteExistente.setNome(dto.nome());
        clienteExistente.setEmail(dto.email());
        // TODO: Implementar demais campos

        Cliente atualizado = clienteRepository.save(clienteExistente);
        return clienteMapper.toResponseDto(atualizado);
    }

    @Override
    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponseDTO buscarPorEmail(String email) {
        return clienteRepository.findByEmail(email)
                .map(clienteMapper::toResponseDto)
                .orElseThrow(() -> new BusinessException(ErroNegocio.CLIENTE_NAO_ENCONTRADO, "Cliente não encontrado"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> listarAtivos() {
        return clienteRepository.findByAtivoTrue()
                .stream()
                .map(clienteMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponseDTO buscarPorNome(String nome) {
        return clienteRepository.findByNomeContainingIgnoreCase(nome)
                .map(clienteMapper::toResponseDto)
                .orElseThrow(() -> new BusinessException(ErroNegocio.CLIENTE_NAO_ENCONTRADO, "Cliente não encontrado"));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorEmail(String email) {
        return clienteRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public List<RankingClienteProjection> listarRankingClientesPorPedidos() {
        return clienteRepository.findRankingClientesPorPedidos();
    }

}
