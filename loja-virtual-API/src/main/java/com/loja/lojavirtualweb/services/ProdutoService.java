package com.lojavirtualweb.services;

import com.lojavirtualweb.domains.Categoria;
import com.lojavirtualweb.domains.Pedido;
import com.lojavirtualweb.domains.Produto;
import com.lojavirtualweb.repositories.CategoriaRepository;
import com.lojavirtualweb.repositories.ProdutoRepository;
import com.lojavirtualweb.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Produto find(Long id){
        Optional<Produto> produto = this.produtoRepository.findById(id);
        return produto.orElseThrow(() -> new ObjectNotFoundException("Não pode encontrar o objeto com id "+id+" Tipo: "+
                Pedido.class.getName()));
    }

    public Page<Produto> search(String nome, List<Long> ids, Integer page, Integer size, String direction, String orderBy ){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), orderBy );
        List<Categoria> categorias = this.categoriaRepository.findAllById(ids);
        return this.produtoRepository.search(nome, categorias,pageRequest);

    }

}
