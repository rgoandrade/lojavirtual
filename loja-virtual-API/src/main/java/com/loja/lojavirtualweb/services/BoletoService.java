package com.lojavirtualweb.services;

import com.lojavirtualweb.domains.PagamentoComBoleto;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class BoletoService {
    /**
     * Coloca vencimento de 30 dias no boleto
     *
     * @param pagamento
     * @param instance
     */
    public void preencherPagamentoComBoleto(PagamentoComBoleto pagamento, Date instance) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(instance);
        calendar.add(Calendar.DAY_OF_MONTH, 30);
        pagamento.setDataVencimento(calendar.getTime());
    }
}
