package com.deliverytech.delivery_api.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deliverytech.delivery_api.entity.Cliente;
import com.deliverytech.delivery_api.entity.ItemPedido;
import com.deliverytech.delivery_api.entity.Pedido;
import com.deliverytech.delivery_api.entity.Produto;
import com.deliverytech.delivery_api.entity.Restaurante;
import com.deliverytech.delivery_api.enums.PerfilUsuario;
import com.deliverytech.delivery_api.enums.StatusPedidoEnum;
import com.deliverytech.delivery_api.repository.ClienteRepository;
import com.deliverytech.delivery_api.repository.PedidoRepository;
import com.deliverytech.delivery_api.repository.ProdutoRepository;
import com.deliverytech.delivery_api.repository.RestauranteRepository;

@Service
public class DataLoaderService {

    
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
    }

    private void loadClientes() {
        Cliente cliente1 = new Cliente(null, "João Silva", "joao@gmail.com", "senha123", "Rua A", "123456789", true, null, PerfilUsuario.CLIENTE);
        Cliente cliente2 = new Cliente(null, "Maria Oliveira", "maria@gmail.com", "senha456", "Rua B", "987654321", true, null, PerfilUsuario.CLIENTE);
        Cliente cliente3 = new Cliente(null, "Carlos Souza", "carlos@gmail.com", "senha789", "Rua C", "456789123", false, null, PerfilUsuario.ADMINISTRADOR);

        clienteRepository.saveAll(List.of(cliente1, cliente2, cliente3));
    }

    private void loadRestaurantes() {
        Restaurante r1 = new Restaurante(null,"Pizzaria Bella Massa","Italiana","Av. Itália, 100",true,new BigDecimal("5.00"));
        Restaurante r2 = new Restaurante(null,"Sushi Yama","Japonesa","Rua do Japão, 200",true,new BigDecimal("8.50"));
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

        Pedido pedido1 = new Pedido(cliente, restaurante, LocalDateTime.now());
        ItemPedido item1 = new ItemPedido(pedido1, produto, 2);
        pedido1.setItens(List.of(item1));
        pedidoRepository.save(pedido1);
    }

    public void validateQueries() {
        validarClientes();
        validarRestaurantes();
        validarProdutos();
        validarPedidos();
    }

    private void validarClientes() {
        System.out.println("Clientes ativos:");
        clienteRepository.findByAtivoTrue()
            .forEach(System.out::println);

        System.out.println("Buscar por email:");
        Cliente cliente = clienteRepository.findByEmail("joao@gmail.com")
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        System.out.println(cliente);

        System.out.println("Nome contendo 'Maria':");
        clienteRepository.findByNomeContainingIgnoreCase("maria")
            .ifPresent(System.out::println);

        System.out.println("Existe email 'joao@gmail.com'? " + clienteRepository.existsByEmail("joao@gmail.com"));
    }

    private void validarRestaurantes() {
        System.out.println("Restaurantes ativos:");
        restauranteRepository.findByAtivoTrue().forEach(System.out::println);

        System.out.println("Por categoria 'Italiana':");
        restauranteRepository.findByCategoria("Italiana").forEach(System.out::println);

        System.out.println("Taxa de entrega até R$ 6,00:");
        restauranteRepository.findByTaxaEntregaLessThanEqual(new BigDecimal("6.00"))
            .forEach(System.out::println);

        System.out.println("Top 5 ordenado por nome:");
        restauranteRepository.findTop5ByOrderByNomeAsc().forEach(System.out::println);
    }

    private void validarProdutos() {
        Long restauranteId = restauranteRepository.findByNome("Pizzaria Bella Massa").get(0).getId();

        System.out.println("Produtos do restaurante Pizzaria Bella Massa:");
        produtoRepository.findByRestauranteId(restauranteId).forEach(System.out::println);

        System.out.println("Produtos disponíveis:");
        produtoRepository.findByDisponivelTrue().forEach(System.out::println);

        System.out.println("Produtos por categoria 'Pizza':");
        produtoRepository.findByCategoria("Pizza").forEach(System.out::println);

        System.out.println("Produtos até R$ 25,00:");
        produtoRepository.findByPrecoLessThanEqual(new BigDecimal("25.00")).forEach(System.out::println);
    }

    private void validarPedidos() {
        Cliente cliente = clienteRepository.findByEmail("joao@gmail.com")
        .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        System.out.println("Pedidos do cliente João:");
        pedidoRepository.findByClienteId(cliente.getId()).forEach(System.out::println);

        System.out.println("Últimos 10 pedidos:");
        pedidoRepository.findTop10ByOrderByDataPedidoDesc().forEach(System.out::println);

        System.out.println("Pedidos RECEBIDO:");
        pedidoRepository.findByStatus(StatusPedidoEnum.ENTREGUE).forEach(System.out::println);

        System.out.println("Pedidos no último dia:");
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime ontem = agora.minusDays(1);
        pedidoRepository.findByDataPedidoBetween(ontem, agora).forEach(System.out::println);
    }




}
