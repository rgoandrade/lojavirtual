package com.lojavirtualweb.services;

import com.lojavirtualweb.domains.ItemPedido;
import com.lojavirtualweb.domains.PagamentoComBoleto;
import com.lojavirtualweb.domains.Pedido;
import com.lojavirtualweb.domains.enums.EstadoPagamento;
import com.lojavirtualweb.repositories.ItemPedidoRepository;
import com.lojavirtualweb.repositories.PagamentoRepository;
import com.lojavirtualweb.repositories.PedidoRepository;
import com.lojavirtualweb.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by Marco Antônio on 22/05/2018
 */
@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EmailService emailService;

    public Pedido find(Long id){

        Optional<Pedido> pedido = pedidoRepository.findById(id);

        return pedido.orElseThrow(() ->
                new ObjectNotFoundException("Não pode encontrar o objeto com id "+id+" Tipo: "+
                        Pedido.class.getName()));
    }


    @Transactional
    public Pedido insert(Pedido pedido) {
        pedido.setId(null);
        pedido.setInstance(new Date());
        pedido.setCliente(this.clienteService.find(pedido.getCliente().getId()));
        pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        pedido.getPagamento().setPedido(pedido);

        if (pedido.getPagamento() instanceof PagamentoComBoleto){
            PagamentoComBoleto pagamento = (PagamentoComBoleto) pedido.getPagamento();
            this.boletoService.preencherPagamentoComBoleto(pagamento, pedido.getInstance());
        }
        pedido = this.pedidoRepository.save(pedido);
        this.pagamentoRepository.save(pedido.getPagamento());
        Pedido finalPedido = pedido;
        pedido.getItens().forEach(itemPedido -> {
           itemPedido.setDesconto(0.0);
           itemPedido.setProduto(this.produtoService.find(itemPedido.getProduto().getId()));
           itemPedido.setPreco(itemPedido.getProduto().getPreco());
           itemPedido.setPedido(finalPedido);
       });
        this.itemPedidoRepository.saveAll(pedido.getItens());
        this.emailService.sendOrderConfirmationHtmlEmail(pedido);
        return pedido;
    }
}
