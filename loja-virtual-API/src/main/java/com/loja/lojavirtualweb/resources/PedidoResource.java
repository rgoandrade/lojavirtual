package com.loja.lojavirtualweb.resources;

import com.loja.lojavirtualweb.domains.Pedido;
import com.loja.lojavirtualweb.services.PedidoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;


@RestController
@RequestMapping("/pedidos")
public class PedidoResource {

    @Autowired
    private PedidoService pedidoService;

    @ApiOperation(value="Busca por Id")
    @GetMapping("{id}")
    public ResponseEntity<Pedido> find(@PathVariable Long id){
        Pedido pedido = this.pedidoService.find(id);
        return ResponseEntity.ok(pedido);
    }

    @ApiOperation(value="Insere Pedido")
    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody Pedido pedido){
        pedido = this.pedidoService.insert(pedido);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("{id}")
                .buildAndExpand(pedido.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

}
