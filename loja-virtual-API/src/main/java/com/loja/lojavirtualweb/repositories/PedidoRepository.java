package com.loja.lojavirtualweb.repositories;

import com.loja.lojavirtualweb.domains.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
