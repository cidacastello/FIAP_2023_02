package br.com.fiap.apicep.controller;

import br.com.fiap.apicep.dto.EnderecoDTO;
import br.com.fiap.apicep.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/cep")
public class EnderecoController {

    @Autowired
    private EnderecoService service;

    //01311000
    //"https://viacep.com.br/ws/{cep}/json"
    @GetMapping("/{cep}")
    public ResponseEntity< EnderecoDTO> findCep(@PathVariable String cep){

        EnderecoDTO endereco = service.getCep(cep);

      return ResponseEntity.ok(endereco);
    }

}
