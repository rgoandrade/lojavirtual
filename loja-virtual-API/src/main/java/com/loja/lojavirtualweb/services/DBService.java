package com.lojavirtualweb.services;

import com.lojavirtualweb.domains.*;
import com.lojavirtualweb.domains.enums.EstadoPagamento;
import com.lojavirtualweb.domains.enums.Perfil;
import com.lojavirtualweb.domains.enums.TipoCliente;
import com.lojavirtualweb.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

/**
 * Created by Marco Antônio on 06/06/2018
 */

@Service
public class DBService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public void instantiateTestDataBase() throws ParseException {

        Categoria cat1 = new Categoria(null, "Informática");
        Categoria cat2 = new Categoria(null, "Escritório");
        Categoria cat3 = new Categoria(null, "Cama Mesa e Banho");
        Categoria cat4 = new Categoria(null, "Eletrônicos");
        Categoria cat5 = new Categoria(null, "Jardinagem");
        Categoria cat6 = new Categoria(null, "Decoração");
        Categoria cat7 = new Categoria(null, "Perfumaria");

        Produto p1 = new Produto(null, "Computado", 2000.00);
        Produto p2 = new Produto(null, "Impressora", 800.00);
        Produto p3 = new Produto(null, "Mouse", 80.00);
        Produto p4 = new Produto(null, "Mesa Escritorio", 300.00);
        Produto p5 = new Produto(null, "Toalha", 50.00);
        Produto p6 = new Produto(null, "Colcha", 200.00);
        Produto p7 = new Produto(null, "TV true color", 1200.00);
        Produto p8 = new Produto(null, "Roçadeira", 800.00);
        Produto p9 = new Produto(null, "Abajour", 100.00);
        Produto p10 = new Produto(null, "Pendente", 180.00);
        Produto p11 = new Produto(null, "Shampoo", 90.00);


        cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
        cat2.getProdutos().addAll(Arrays.asList(p2, p4));
        cat3.getProdutos().addAll(Arrays.asList(p5, p6));
        cat4.getProdutos().addAll(Arrays.asList(p1, p2, p3, p7));
        cat5.getProdutos().addAll(Arrays.asList(p8));
        cat6.getProdutos().addAll(Arrays.asList(p9, p10));
        cat7.getProdutos().addAll(Arrays.asList(p11));

        p1.getCategorias().addAll(Arrays.asList(cat1, cat4));
        p2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat4));
        p3.getCategorias().addAll(Arrays.asList(cat1, cat4));
        p4.getCategorias().addAll(Arrays.asList(cat2));
        p5.getCategorias().addAll(Arrays.asList(cat3));
        p6.getCategorias().addAll(Arrays.asList(cat3));
        p7.getCategorias().addAll(Arrays.asList(cat4));
        p8.getCategorias().addAll(Arrays.asList(cat5));
        p9.getCategorias().addAll(Arrays.asList(cat6));
        p10.getCategorias().addAll(Arrays.asList(cat6));
        p11.getCategorias().addAll(Arrays.asList(cat7));

        this.categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));

        this.produtoRepository.saveAll(Arrays.asList(p1,p2,p3,p4,p5,p6,p7));


        Estado est1 = new Estado(null, "Minas Gerais");
        Estado est2 = new Estado(null, "São Paulo");


        Cidade c1 = new Cidade(null, "Uberlândia",est1);
        Cidade c2 = new Cidade(null, "São Paulo", est2);
        Cidade c3 = new Cidade(null, "Campinas", est2);

        est1.getCidades().addAll(Arrays.asList(c1));
        est2.getCidades().addAll(Arrays.asList(c2, c3));

        this.estadoRepository.saveAll(Arrays.asList(est1, est2));

        this.cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

        Cliente cli1 = new Cliente(null, "Marco Antônio", "marco-oliveira@live.com",
                "36378912377", TipoCliente.PESSOAFISICA, encoder.encode("123456"));

        cli1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));

        Cliente cli2 = new Cliente(null, "Marco", "m4rco.oliveir4@gmail.com",
                "12046888006", TipoCliente.PESSOAFISICA, encoder.encode("123456"));

        cli2.getTelefones().addAll(Arrays.asList("54545898", "98556552"));
        cli2.addPerfil(Perfil.ADMIN);

        Endereco e1 = new Endereco(null, "Rua Flores", "300",
                "Apto 203", "Jardim", "38220834", c1, cli1);
        Endereco e2 = new Endereco(null, "Avenida Matos", "105",
                "Sala 800", "Centro", "38777012", c2, cli1);

        Endereco e3 = new Endereco(null, "Avenida Matos", "105",
                null, "Centro", "38777012", c2, cli2);

        cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
        cli1.getEnderecos().addAll(Arrays.asList(e3));
        this.clienteRepository.saveAll(Arrays.asList(cli1, cli2));

        this.enderecoRepository.saveAll(Arrays.asList(e1, e2, e3));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
        Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);

        Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
        ped1.setPagamento(pagto1);

        Pagamento pagto2 = new PagamentoComBoleto(null,EstadoPagamento.PENDENTE,
                ped2, sdf.parse("20/10/2017 00:00"),null);

        ped2.setPagamento(pagto2);

        cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

        this.pedidoRepository.saveAll(Arrays.asList(ped1, ped2));

        this.pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));


        ItemPedido ip1 = new ItemPedido(ped1, p1, 200.00, 1, 2000.00);

        ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);

        ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);

        this.itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));



    }
}
