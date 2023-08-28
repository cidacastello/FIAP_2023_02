package br.com.fiap.mspagamentos.service;

import br.com.fiap.mspagamentos.dto.PagamentoDTO;
import br.com.fiap.mspagamentos.model.Pagamento;
import br.com.fiap.mspagamentos.model.Status;
import br.com.fiap.mspagamentos.repository.PagamentoRepository;
import br.com.fiap.mspagamentos.service.exception.DatabaseException;
import br.com.fiap.mspagamentos.service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository repository;

    @Transactional(readOnly = true)  //do spring
    public List<PagamentoDTO> findAll() {
        List<Pagamento> list = repository.findAll();
        //converter Pagamento para PagamentoDTO
        //usando expressão lambda
        return list.stream().map(x -> new PagamentoDTO(x)).collect(Collectors.toList());
        //return list.stream().map(PagamentoDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PagamentoDTO findById(Long id) {
        // lança exception customizada
        Pagamento pagamento = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado! Id: " + id));
        //converter pagamento para dto
        PagamentoDTO dto = new PagamentoDTO(pagamento);
        return dto;
    }

    @Transactional
    public PagamentoDTO insert(PagamentoDTO dto) {
        Pagamento entity = new Pagamento();
        //método auxiliar para converter DTO para entity
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new PagamentoDTO(entity);
    }

    @Transactional
    public PagamentoDTO update(Long id, PagamentoDTO dto) {

        try {
            //não vai no DB, obj monitorado pela JPA
            Pagamento entity = repository.getReferenceById(id);
            //método auxiliar para converter DTO para entity
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new PagamentoDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado. Id: " + id);
        }
    }

   /* @Transactional
    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }*/

    @Transactional
    public void delete(Long id) {
        if(!repository.existsById(id)){
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Recurso não encontrado");
        }
    }

    @Transactional
    public void confirmarPagamento(Long id) {
        //recupera o pagamento no DB
        Optional<Pagamento> pagamento = repository.findById(id);
        //valida se existe o pagamento
        if (!pagamento.isPresent()) {
            throw new ResourceNotFoundException("Recurso não encontrada");
        }
        //seta o status do pagamento como confirmado
        pagamento.get().setStatus(Status.CONFIRMADO);
        //salva a alteração no DB
        repository.save(pagamento.get());
    }

    @Transactional
    public void cancelarPagamento(Long id) {
        //recupera o pagamento no DB
        Optional<Pagamento> pagamento = repository.findById(id);
        //valida se existe o pagamento
        if (!pagamento.isPresent()) {
            throw new ResourceNotFoundException("Recurso não encontrada");
        }
        //seta o status do pagamento como confirmado
        pagamento.get().setStatus(Status.CANCELADO);
        //salva a alteração no DB
        repository.save(pagamento.get());
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



/*

 @Transactional(readOnly = true)
    public List<Pagamento> findAll() {
        List<Pagamento> list = repository.findAll();
        return list;
    }

    ***
 @Transactional(readOnly = true)
    public List<PagamentoDTO> findAll() {
        List<Pagamento> list = repository.findAll();
        List<PagamentoDTO> pagamentos = new ArrayList<>();
        int index = 0;
        for(Pagamento item : list){
            PagamentoDTO dto = new PagamentoDTO();
            dto.setId(list.get(index).getId());
            dto.setNome(list.get(index).getNome());
            dto.setValor(list.get(index).getValor());
            //fazer para todos os atributos
            pagamentos.add(dto);
            index++;
        }
        return pagamentos;
    }

        @Transactional(readOnly = true)
    public PagamentoDTO findById(Long id){
        //pode não encontrar registro
        Optional<Pagamento> result = repository.findById(id);
        Pagamento pagamento = result.get(); //pega o que está dentro de result
        //converter pagamento para dto
        PagamentoDTO dto = new PagamentoDTO(pagamento);
        return dto;
    }
 */



