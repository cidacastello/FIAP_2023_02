package br.com.fiap.mspagamentos.controller;

import br.com.fiap.mspagamentos.dto.PagamentoDTO;
import br.com.fiap.mspagamentos.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService service;

    @GetMapping
    public ResponseEntity<List<PagamentoDTO>> findAll() {
        List<PagamentoDTO> dto = service.findAll();
        return ResponseEntity.ok(dto) ;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PagamentoDTO> findById(@PathVariable Long id){
        PagamentoDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<PagamentoDTO> insert(@Valid @RequestBody PagamentoDTO dto){

        dto = service.insert(dto);
        //devolve a URI
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoDTO> update(@PathVariable @Valid @Positive Long id,
                                               @Valid @RequestBody PagamentoDTO dto){

        dto = service.update(id, dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    //Patch - alterar somente o status do pedido
    @PatchMapping("/{id}/confirmar")
    public void confirmarPagamento(@PathVariable Long id){
        service.confirmarPagamento(id);
    }


    //Patch - alterar somente o status do pedido
    @PatchMapping("/{id}/cancelar")
    public void cancelarPagamento(@PathVariable Long id){
        service.cancelarPagamento(id);
    }
}


