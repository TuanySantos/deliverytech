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

import jakarta.validation.ValidationException;

@Service
@Transactional
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteMapper clienteMapper;

    @Override
    public ClienteResponseDTO cadastrarCliente(ClienteRequestDTO dto) {
        if (dto.nome() == null || dto.nome().isBlank()) {
            throw new ValidationException("Nome do cliente é obrigatório");
        }
        if (dto.email() == null || dto.email().isBlank()) {
            throw new ValidationException("Email do cliente é obrigatório");
        }
        if (clienteRepository.existsByEmail(dto.email())) {
            throw new BusinessException(ErroNegocio.EMAIL_JA_CADASTRADO, "Email já cadastrado");
        }
        Cliente cliente = clienteMapper.toEntity(dto);
        Cliente salvo = clienteRepository.save(cliente);
        return clienteMapper.toResponseDto(salvo);
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponseDTO buscarClientePorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErroNegocio.CLIENTE_NAO_ENCONTRADO, "Cliente não encontrado"));
        return clienteMapper.toResponseDto(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponseDTO buscarClientePorEmail(String email) {
        return clienteRepository.findByEmail(email)
                .map(clienteMapper::toResponseDto)
                .orElseThrow(() -> new BusinessException(ErroNegocio.CLIENTE_NAO_ENCONTRADO, "Cliente não encontrado"));
    }

    @Override
    public ClienteResponseDTO atualizarCliente(Long id, ClienteRequestDTO dto) {
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErroNegocio.CLIENTE_NAO_ENCONTRADO, "Cliente não encontrado"));
        if (dto.nome() == null || dto.nome().isBlank()) {
            throw new BusinessException(ErroNegocio.PEDIDO_INVALIDO, "Nome do cliente é obrigatório");
        }
        if (dto.email() == null || dto.email().isBlank()) {
            throw new BusinessException(ErroNegocio.PEDIDO_INVALIDO, "Email do cliente é obrigatório");
        }
        if (!clienteExistente.getEmail().equals(dto.email()) && clienteRepository.existsByEmail(dto.email())) {
            throw new BusinessException(ErroNegocio.EMAIL_JA_CADASTRADO, "Email já cadastrado");
        }
        clienteExistente.setNome(dto.nome());
        clienteExistente.setEmail(dto.email());
        clienteExistente.setSenha(dto.senha());
        clienteExistente.setEndereco(dto.endereco());
        clienteExistente.setTelefone(dto.telefone());
        Cliente atualizado = clienteRepository.save(clienteExistente);
        return clienteMapper.toResponseDto(atualizado);
    }

    @Override
    public void ativarDesativarCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErroNegocio.CLIENTE_NAO_ENCONTRADO, "Cliente não encontrado"));
        cliente.setAtivo(!cliente.isAtivo());
        clienteRepository.save(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> listarClientesAtivos() {
        return clienteRepository.findByAtivoTrue()
                .stream()
                .map(clienteMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RankingClienteProjection> listarRankingClientesPorPedidos() {
        return clienteRepository.findRankingClientesPorPedidos();
    }

}
