package com.loja.lojavirtualweb.domains;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.loja.lojavirtualweb.domains.enums.EstadoPagamento;

import javax.persistence.Entity;


@Entity
@JsonTypeName("pagamentoComCartao")
public class PagamentoComCartao extends Pagamento {

    private static final long serialVersionUID = 5466190406393694181L;

    private Integer numeroDeParcelas;

    public PagamentoComCartao() {}

    public PagamentoComCartao(Long id, EstadoPagamento estado, Pedido pedido, Integer numeroDeParcelas) {
        super(id, estado, pedido);
        this.numeroDeParcelas = numeroDeParcelas;
    }

    public Integer getNumeroDeParcelas() {
        return numeroDeParcelas;
    }

    public void setNumeroDeParcelas(Integer numeroDeParcelas) {
        this.numeroDeParcelas = numeroDeParcelas;
    }
}
