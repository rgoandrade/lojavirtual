package com.loja.lojavirtualweb.repositories;

import com.loja.lojavirtualweb.domains.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
}
