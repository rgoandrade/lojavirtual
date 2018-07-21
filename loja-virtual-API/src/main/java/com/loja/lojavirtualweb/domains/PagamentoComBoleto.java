package com.loja.lojavirtualweb.domains;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.marco.virtualstore.domains.enums.EstadoPagamento;

import javax.persistence.Entity;
import java.util.Date;


@Entity
@JsonTypeName("pagamentoComBoleto")
public class PagamentoComBoleto extends Pagamento {

    private static final long serialVersionUID = 3678465939724470101L;
	
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dataVencimento;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dataPagamento;

    public PagamentoComBoleto(){}

    public PagamentoComBoleto(Long id, EstadoPagamento estado, Pedido pedido, Date dataVencimento, Date dataPagamento) {
        super(id, estado, pedido);
        this.dataVencimento = dataVencimento;
        this.dataPagamento = dataPagamento;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }
}
