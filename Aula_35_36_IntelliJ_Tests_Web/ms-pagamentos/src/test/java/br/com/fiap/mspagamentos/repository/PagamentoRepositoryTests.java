package br.com.fiap.mspagamentos.repository;

import br.com.fiap.mspagamentos.model.Pagamento;
import br.com.fiap.mspagamentos.service.exception.ResourceNotFoundException;
import br.com.fiap.mspagamentos.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test; //JUnit 5
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

@DataJpaTest
public class PagamentoRepositoryTests {

    @Autowired
    private PagamentoRepository repository;

    private Long existingId;
    private Long idNaoExiste;
    private Long countTotalPagamento;

    //vai ser executado antes de cada teste
    @BeforeEach
    void setup() throws Exception {
        existingId = 1L;
        idNaoExiste = 10L;
        countTotalPagamento = 2L;
    }

    @Test
    @DisplayName("Deveria excluir pagamento quando o Id existe")
    public void deleteShouldDeletObjectWhenIdExists() {
        // Act
        repository.deleteById(existingId);
        // Assert
        Optional<Pagamento> result = repository.findById(existingId);
        Assertions.assertFalse(result.isPresent()); //testa se existe um obj dentro do Optional
    }

    @Test //válido para Spring Boot 2.x - no Sprint Boot 3.x Não funciona
    @DisplayName("Deveria lançar EmptyResultDataAccessException quando Id não existe ")
    public void deleteDeveriaLancarEmptyResultDataAccessExceptionQuandoIdNaoExiste() {
        //      deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            repository.deleteById(idNaoExiste);
        });
    }

    @Test
    @DisplayName("save Deveria salvar objeto com auto incremento quando Id é null") //save novo objeto
    public void saveShouldPersistWithAutoIncrementWhenIdIsNull() {

        Pagamento pagamento = Factory.createPagamento();
        pagamento.setId(null);
        pagamento = repository.save(pagamento);
        Assertions.assertNotNull(pagamento.getId());
        //verifica se é o próximo ID
        Assertions.assertEquals(countTotalPagamento + 1, pagamento.getId());
    }

    @Test
    @DisplayName("findfById deveria retornar um Optional<Pagamento> quando Id existe")
    public void findByIdShouldReturnOptionalPagamentoWhenIdExixts(){

        Optional<Pagamento> pagamento = repository.findById(existingId);
        Assertions.assertTrue(pagamento.isPresent());
    }
    @Test
    @DisplayName("findfById deveria retornar um Optional<Pagamento> quando Id não existe")
    public void findByIdShouldReturnEmptyOptionalPagamentoWhenIdNonExixts(){

        Optional<Pagamento> pagamento = repository.findById(idNaoExiste);
        Assertions.assertFalse(pagamento.isPresent());
    }

}

