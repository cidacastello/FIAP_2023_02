package br.com.fiap.mspagamentos.service;

import br.com.fiap.mspagamentos.dto.PagamentoDTO;
import br.com.fiap.mspagamentos.model.Pagamento;
import br.com.fiap.mspagamentos.model.Status;
import br.com.fiap.mspagamentos.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository repository;

    @Transactional(readOnly = true)
    public List<PagamentoDTO> findAll() {
        List<Pagamento> list = repository.findAll();
        //converter Pagamento para PagamentoDTO
        //usando expressão lambda
       // return list.stream().map(x -> new PagamentoDTO(x)).collect(Collectors.toList());

        return list.stream().map(PagamentoDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PagamentoDTO findById(Long id){
        // lança exception
        Optional<Pagamento> result = repository.findById(id);
        Pagamento pagamento = result.get();
        PagamentoDTO dto = new PagamentoDTO(pagamento);
        return dto;

    }

    @Transactional
    public PagamentoDTO insert (PagamentoDTO dto){
        Pagamento entity = new Pagamento();
        //método auxiliar para converter DTO para entity
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new PagamentoDTO(entity);
    }

    //método auxiliar para converter DTO para entity
    private void copyDtoToEntity(PagamentoDTO dto, Pagamento entity) {
        entity.setValor(dto.getValor());
        entity.setNome(dto.getNome());
        entity.setNumeroDoCartao(dto.getNumeroDoCartao());
        entity.setValidade(dto.getValidade());
        entity.setCodigo(dto.getCodigo());
        entity.setStatus(dto.getStatus());
        entity.setPedidoId(dto.getPedidoId());
        entity.setFormaDePagamentoId(dto.getFormaDePagamentoId());
        //setando o status do pedido
        entity.setStatus(Status.CRIADO);
    }

}
