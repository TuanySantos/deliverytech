package com.deliverytech.delivery_api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deliverytech.delivery_api.entity.Cliente;
import com.deliverytech.delivery_api.entity.ItemPedido;
import com.deliverytech.delivery_api.entity.Pedido;
import com.deliverytech.delivery_api.entity.Produto;
import com.deliverytech.delivery_api.entity.Restaurante;
import com.deliverytech.delivery_api.enums.PerfilUsuario;
import com.deliverytech.delivery_api.enums.StatusPedido;
import com.deliverytech.delivery_api.repository.ClienteRepository;
import com.deliverytech.delivery_api.repository.PedidoRepository;
import com.deliverytech.delivery_api.repository.ProdutoRepository;
import com.deliverytech.delivery_api.repository.RestauranteRepository;

@Service
@Transactional
public class DataLoaderService {

    private static final Logger log = LoggerFactory.getLogger(DataLoaderService.class);
    
    @Autowired
    private com.deliverytech.delivery_api.mapper.ClienteMapper clienteMapper;
    
    @Autowired
    private com.deliverytech.delivery_api.mapper.ProdutoMapper produtoMapper;
    
    @Autowired
    private com.deliverytech.delivery_api.mapper.PedidoMapper pedidoMapper;
    
    @Autowired
    private com.deliverytech.delivery_api.mapper.RestauranteMapper restauranteMapper;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

        public void loadTestData() {

        loadClientes();
        loadRestaurantes();
        loadProdutos();
        loadPedidos();
        validateQueries();
    executarCenariosTeste();
    }


    private void logClienteDTO(com.deliverytech.delivery_api.dto.responseDto.ClienteResponseDTO dto) {
        log.info("ClienteDTO: id={}, nome={}, email={}, endereco={}, telefone={}, ativo={}",
            dto.id(), dto.nome(), dto.email(), dto.endereco(), dto.telefone(), dto.ativo());
    }

    private void logProdutoDTO(com.deliverytech.delivery_api.dto.responseDto.ProdutoResponseDTO dto) {
        log.info("ProdutoDTO: id={}, nome={}, descricao={}, preco={}, categoria={}, disponivel={}, restauranteNome={}",
            dto.id(), dto.nome(), dto.descricao(), dto.preco(), dto.categoria(), dto.disponivel(), dto.restauranteNome());
    }

    private void logPedidoDTO(com.deliverytech.delivery_api.dto.responseDto.PedidoResponseDTO dto) {
        log.info("PedidoDTO: id={}, clienteNome={}, restauranteNome={}, dataPedido={}, itens={}",
            dto.id(), dto.clienteNome(), dto.restauranteNome(), dto.dataPedido(), dto.itens());
    }


    private void logRestauranteDTO(com.deliverytech.delivery_api.dto.responseDto.RestauranteResponseDTO dto) {
        log.info("RestauranteDTO: id={}, nome={}, categoria={}, endereco={}, taxaEntrega={}, ativo={}",
            dto.id(), dto.nome(), dto.categoria(), dto.endereco(), dto.taxaEntrega(), dto.ativo());
    }

    private void loadClientes() {
        Cliente cliente1 = new Cliente(null, "João Silva", "joao@gmail.com", "senha123", "Rua A", "123456789", true, null, PerfilUsuario.CLIENTE);
        Cliente cliente2 = new Cliente(null, "Maria Oliveira", "maria@gmail.com", "senha456", "Rua B", "987654321", true, null, PerfilUsuario.CLIENTE);
        Cliente cliente3 = new Cliente(null, "Carlos Souza", "carlos@gmail.com", "senha789", "Rua C", "456789123", false, null, PerfilUsuario.ADMINISTRADOR);

        clienteRepository.saveAll(List.of(cliente1, cliente2, cliente3));
    }

    private void loadRestaurantes() {
        Restaurante r1 = new Restaurante(null,"Pizzaria Bella Massa","Italiana","Av. Itália, 100",true,new BigDecimal("5.00"), "(11)9888-0544");
        Restaurante r2 = new Restaurante(null,"Sushi Yama","Japonesa","Rua do Japão, 200",true,new BigDecimal("8.50"), "(11)95874-0521");
        restauranteRepository.saveAll(List.of(r1, r2));
    }

    private List<Produto> produtos;

    private void loadProdutos() {
        produtos = Arrays.asList(
            new Produto(null, "Pizza Margherita", "Tradicional com molho e queijo", new BigDecimal("30.00"), "Pizza", true, restauranteRepository.findByNome("Pizzaria Bella Massa").get(0)),
            new Produto(null, "Pizza Calabresa", "Com cebola e azeitonas", new BigDecimal("32.00"), "Pizza", true, restauranteRepository.findByNome("Pizzaria Bella Massa").get(0)),
            new Produto(null, "Temaki de Salmão", "Cone com arroz e salmão", new BigDecimal("22.00"), "Temaki", true, restauranteRepository.findByNome("Sushi Yama").get(0)),
            new Produto(null, "Sashimi", "Fatias de salmão fresco", new BigDecimal("28.00"), "Sushi", true, restauranteRepository.findByNome("Sushi Yama").get(0)),
            new Produto(null, "Hot Roll", "Enrolado frito recheado", new BigDecimal("26.00"), "Sushi", true, restauranteRepository.findByNome("Sushi Yama").get(0))
        );

        produtoRepository.saveAll(produtos);
    }

    private void loadPedidos() {
        Cliente cliente = clienteRepository.findAll().get(0);
        Restaurante restaurante = restauranteRepository.findAll().get(0);
        Produto produto = produtoRepository.findAll().get(0);

        Pedido pedido1 = new Pedido();
        pedido1.setCliente(cliente);
        pedido1.setRestaurante(restaurante);
        pedido1.setDataPedido(LocalDateTime.now());
        pedido1.setStatus(StatusPedido.PENDENTE);
        pedido1.setInicio(LocalDateTime.now());
        pedido1.setFim(LocalDateTime.now().plusHours(1));
        

        ItemPedido item1 = new ItemPedido();
        item1.setPedido(pedido1);
        item1.setProduto(produto);
        item1.setQuantidade(2);
        item1.setPreco(new BigDecimal("50"));

        pedido1.setItens(List.of(item1));

        BigDecimal valorTotal = item1.getProduto().getPreco().multiply(BigDecimal.valueOf(item1.getQuantidade()));
        pedido1.setValorTotal(valorTotal);

        pedidoRepository.save(pedido1);
    }

    public void validateQueries() {
        validarClientes();
        validarRestaurantes();
        validarProdutos();
        validarPedidos();
    }

    private void validarClientes() {
        log.info("Clientes ativos:");
        Iterable<com.deliverytech.delivery_api.dto.responseDto.ClienteResponseDTO> clientesDTO = clienteMapper.toResponseDtoList(clienteRepository.findByAtivoTrue());
        for (com.deliverytech.delivery_api.dto.responseDto.ClienteResponseDTO dto : clientesDTO) {
            logClienteDTO(dto);
        }

        log.info("Buscar por email:");
        clienteRepository.findByEmail("joao@gmail.com")
            .map(clienteMapper::toResponseDto)
            .ifPresent(this::logClienteDTO);

        log.info("Nome contendo 'Maria':");
        clienteRepository.findByNomeContainingIgnoreCase("maria")
            .map(clienteMapper::toResponseDto)
            .ifPresent(this::logClienteDTO);

        log.info("Existe email 'joao@gmail.com'? {}", clienteRepository.existsByEmail("joao@gmail.com"));
    }

    private void validarRestaurantes() {
        log.info("Restaurantes ativos:");
        restauranteMapper.toResponseDtoList(restauranteRepository.findByAtivoTrue())
            .forEach(this::logRestauranteDTO);

        log.info("Por categoria 'Italiana':");
        restauranteMapper.toResponseDtoList(restauranteRepository.findByCategoria("Italiana"))
            .forEach(this::logRestauranteDTO);

        log.info("Taxa de entrega até R$ 6,00:");
        restauranteMapper.toResponseDtoList(restauranteRepository.findByTaxaEntregaLessThanEqual(new BigDecimal("6.00")))
            .forEach(this::logRestauranteDTO);

        log.info("Top 5 ordenado por nome:");
        restauranteMapper.toResponseDtoList(restauranteRepository.findTop5ByOrderByNomeAsc())
            .forEach(this::logRestauranteDTO);
    }

    private void validarProdutos() {
    Long restauranteId = restauranteRepository.findByNome("Pizzaria Bella Massa").get(0).getId();

        log.info("Produtos do restaurante Pizzaria Bella Massa:");
        produtoMapper.toResponseDtoList(produtoRepository.findByRestauranteId(restauranteId))
            .forEach(this::logProdutoDTO);

        log.info("Produtos disponíveis:");
        produtoMapper.toResponseDtoList(produtoRepository.findByDisponivelTrue())
            .forEach(this::logProdutoDTO);

        log.info("Produtos por categoria 'Pizza':");
        produtoMapper.toResponseDtoList(produtoRepository.findByCategoria("Pizza"))
            .forEach(this::logProdutoDTO);

        log.info("Produtos até R$ 25,00:");
        produtoMapper.toResponseDtoList(produtoRepository.findByPrecoLessThanEqual(new BigDecimal("25.00")))
            .forEach(this::logProdutoDTO);
    }

    private void validarPedidos() {
    Cliente cliente = clienteRepository.findByEmail("joao@gmail.com")
    .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        log.info("Pedidos do cliente João:");
        pedidoMapper.toResponseDtoList(pedidoRepository.findByClienteId(cliente.getId()))
            .forEach(this::logPedidoDTO);

        log.info("Últimos 10 pedidos:");
        pedidoMapper.toResponseDtoList(pedidoRepository.findTop10ByOrderByDataPedidoDesc())
            .forEach(this::logPedidoDTO);

        log.info("Pedidos RECEBIDO:");
        pedidoMapper.toResponseDtoList(pedidoRepository.findByStatus(StatusPedido.ENTREGUE))
            .forEach(this::logPedidoDTO);

        log.info("Pedidos no último dia:");
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime ontem = agora.minusDays(1);
        pedidoMapper.toResponseDtoList(pedidoRepository.findByDataPedidoBetween(ontem, agora))
            .forEach(this::logPedidoDTO);
    }

       // =================== Cenários de Teste Obrigatórios ===================
    public void executarCenariosTeste() {
        log.info("CENÁRIOS DE TESTE OBRIGATÓRIOS");

        //Cenário 1: Busca de Cliente por Email
        log.info("Cenário 1: Busca de Cliente por Email");
        clienteRepository.findByEmail("joao@email.com")
            .map(clienteMapper::toResponseDto)
            .ifPresentOrElse(this::logClienteDTO, () -> log.info("Cliente NÃO encontrado!"));

        //Cenário 2: Produtos por Restaurante
        log.info("Cenário 2: Produtos por Restaurante");
    List<com.deliverytech.delivery_api.dto.responseDto.ProdutoResponseDTO> produtos = produtoMapper.toResponseDtoList(produtoRepository.findByRestauranteId(1L));
    log.info("Produtos do restaurante 1:");
    produtos.forEach(this::logProdutoDTO);

        //Cenário 3: Pedidos Recentes
        log.info("Cenário 3: Pedidos Recentes");
    List<com.deliverytech.delivery_api.dto.responseDto.PedidoResponseDTO> pedidos = pedidoMapper.toResponseDtoList(pedidoRepository.findTop10ByOrderByDataPedidoDesc());
    log.info("Pedidos mais recentes:");
    pedidos.forEach(this::logPedidoDTO);

        //Cenário 4: Restaurantes por Taxa
        log.info("Cenário 4: Restaurantes por Taxa");
        List<com.deliverytech.delivery_api.dto.responseDto.RestauranteResponseDTO> restaurantes = restauranteMapper.toResponseDtoList(
            restauranteRepository.findByTaxaEntregaLessThanEqual(new BigDecimal("5.00")));
        log.info("Restaurantes com taxa até R$ 5,00:");
        restaurantes.forEach(this::logRestauranteDTO);
    }

}
