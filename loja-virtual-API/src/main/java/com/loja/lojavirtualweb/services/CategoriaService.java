package com.lojavirtualweb.services;

import com.lojavirtualweb.domains.Categoria;
import com.lojavirtualweb.dtos.CategoriaDto;
import com.lojavirtualweb.repositories.CategoriaRepository;
import com.lojavirtualweb.services.exceptions.DataIntegrityException;
import com.lojavirtualweb.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by Marco Antônio on 18/05/2018
 */

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria find(Long id) {
        Optional<Categoria> categoria = this.categoriaRepository.findById(id);
        return categoria.orElseThrow(() ->
                new ObjectNotFoundException("Objeto não encontrado! Id: "+id+", Tipo: "+Categoria.class.getName()));
    }

    public Categoria insert(Categoria categoria) {
        categoria.setId(null); //Garantir que sera salva uma categoria nova
        return this.categoriaRepository.save(categoria);

    }

    public Categoria update(Categoria categoria) {
        Categoria newCategoria = find(categoria.getId());
        updateData(newCategoria, categoria);
        return this.categoriaRepository.save(newCategoria);
    }

    private void updateData(Categoria newCategoria, Categoria categoria) {
        newCategoria.setNome(categoria.getNome());

    }

    public void delete(Long id) {
        find(id);
        try {
            this.categoriaRepository.deleteById(id);
        } catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Categoria com produto não pode ser apagada.");
        }
    }

    public List<Categoria> findAll() {

        return this.categoriaRepository.findAll();
    }

    public Page<Categoria> findPage(Integer page, Integer size, String direction, String orderBy ){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), orderBy);
        return this.categoriaRepository.findAll(pageRequest);
    }

    public Categoria fromDto(CategoriaDto categoriaDto) {

        return new Categoria(categoriaDto.getId(), categoriaDto.getNome());

    }
}

