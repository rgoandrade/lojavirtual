package com.loja.lojavirtualweb.repositories;

import com.loja.lojavirtualweb.domains.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
