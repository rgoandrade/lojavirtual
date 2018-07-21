package com.loja.lojavirtualweb.repositories;

import com.loja.lojavirtualweb.domains.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
}
