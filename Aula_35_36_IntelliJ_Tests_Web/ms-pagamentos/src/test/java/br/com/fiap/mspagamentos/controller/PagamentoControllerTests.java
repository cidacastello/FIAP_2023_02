package br.com.fiap.mspagamentos.controller;

import br.com.fiap.mspagamentos.dto.PagamentoDTO;
import br.com.fiap.mspagamentos.model.Status;
import br.com.fiap.mspagamentos.service.PagamentoService;
import br.com.fiap.mspagamentos.service.exception.ResourceNotFoundException;
import br.com.fiap.mspagamentos.tests.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.List;
//Para melhorar a legibilidade do código, vamos importar estaticamente os métodos
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PagamentoController.class)
public class PagamentoControllerTests {
    //declarações
    @Autowired
    private MockMvc mockMvc; //para chamar endpoint

    //controlador tem dependêcia do service
    //dependência - mockada
    @MockBean
    private PagamentoService service;
    private PagamentoDTO pagamentoDTO;
    private Long idExiste;
    private Long idNaoExiste;
    // converter para JSON o objeto java e enviar na requisição
    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    void setup() throws Exception {

        //criando um PagamentoDTO
        pagamentoDTO = Factory.createPagamentoDTO();

        //listando PagamentoDTO
        List<PagamentoDTO> list = List.of(pagamentoDTO);

        //simulando o comportamento do service
        when(service.findAll()).thenReturn(list);

        idExiste = 1L;
        idNaoExiste = 10L;

        // findById
        when(service.findById(idExiste)).thenReturn(pagamentoDTO);
        //para findById não existe - lança exception
        when(service.findById(idNaoExiste)).thenThrow(ResourceNotFoundException.class);

        //insert
        // any() - qualquer objeto
        when(service.insert(any())).thenReturn(pagamentoDTO);


        //update
        // qdo usa o any, não pode usar objetos simples, precisamos usa o eq()
        when(service.update(eq(idExiste), any())).thenReturn(pagamentoDTO);
        when(service.update(eq(idNaoExiste), any())).thenThrow(ResourceNotFoundException.class);

        //quando o retorno é void
        //delete
        doNothing().when(service).delete(idExiste);
        doThrow(ResourceNotFoundException.class).when(service).delete(idNaoExiste);

    }

    //teste simulando o comportamento do service
    @Test
    @DisplayName("findAll Deveria Retornar Uma Lista Do Tipo PagamentoDTO")
    public void findAllDeveriaRetornarUmaListaDoTipoPagamentoDTO() throws Exception {

        // chamando uma requisição com o método get no caminho /pagamentos
        ResultActions resultado = mockMvc.perform(get("/pagamentos")
                .accept(MediaType.APPLICATION_JSON));
        resultado.andExpect(status().isOk());
    }

    @Test
    @DisplayName("findById Deve Retornar PagamentoDTO Quando Id Existe")
    public void findByIdDeveRetornarPagamentoDTOQuandoIdExiste() throws Exception {

        ResultActions resultado = mockMvc.perform(get("/pagamentos/{id}", idExiste)
                .accept(MediaType.APPLICATION_JSON));
        //assertions
        resultado.andExpect(status().isOk());
        // analisa se tem os campos na resposta
        // $ - acessar o objeto da resposta
        resultado.andExpect(jsonPath("$.id").exists());
        resultado.andExpect(jsonPath("$.valor").exists());
        resultado.andExpect(jsonPath("$.status").exists());
    }

    @Test
    @DisplayName("findById Deve Retornar Not Found Quando Id Não Existe")
    public void findByIdDeveRetornarNotFoundQuandoIdNaoExiste() throws Exception {
        //NotFound - código 404
        ResultActions resultado = mockMvc.perform(get("/pagamentos/{id}", idNaoExiste)
                .accept(MediaType.APPLICATION_JSON));
        //assertions
        resultado.andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("insert Deve Retornar Created PagamentoDTO com ID")
    public void insertDeveRetornarCreatedPagamentoDTOComId() throws Exception {
        // retornar status 201
        PagamentoDTO dto = new PagamentoDTO(null, BigDecimal.valueOf(32.25), "Bach", "322345698",
                "07/25", "547", Status.CRIADO, 1L, 2L);
        String corpoJson = objectMapper.writeValueAsString(dto);

        ResultActions resultado = mockMvc.perform(post("/pagamentos")
                .content(corpoJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        resultado.andExpect(status().isCreated());
        resultado.andDo(print());
        resultado.andExpect(header().exists("Location"));
        resultado.andExpect(jsonPath("$.id").exists());
        resultado.andExpect(jsonPath("$.valor").exists());
        resultado.andExpect(jsonPath("$.status").exists());
    }

    @Test
    @DisplayName("update Deve Retornar PagamentoDTO Quando Id Existe")
    public void updateDeveriaRetornarPagamentoDTOQuandoIdExiste() throws Exception {
        //precisamos passar o corpo da requisição
        //objeto JSON, converter obj com Bean ObjectMapper
        String corpoJson = objectMapper.writeValueAsString(pagamentoDTO);

        ResultActions resultado = mockMvc.perform(put("/pagamentos/{id}", idExiste)
                .content(corpoJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        resultado.andExpect(status().isOk());
        resultado.andDo(print());
        resultado.andExpect(jsonPath("$.id").exists());
        resultado.andExpect(jsonPath("$.valor").exists());
        resultado.andExpect(jsonPath("$.status").exists());
    }

    @Test
    @DisplayName("update Deve Retornar NotFound Quando Id Não Existe")
    public void updateDeveriaRetornarNotFoundQuandoIdNaoExiste() throws Exception {
        //precisamos passar o corpo da requisição
        //objeto JSON, converter obj com Bean ObjectMapper
        String corpoJson = objectMapper.writeValueAsString(pagamentoDTO);

        ResultActions resultado = mockMvc.perform(put("/pagamentos/{id}", idNaoExiste)
                .content(corpoJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        resultado.andExpect(status().isNotFound());
        resultado.andDo(print());
    }

    @Test
    @DisplayName("delete Deve Retornar NoContent Quando Id Existe")
    public void deleteDeveRetornarNoContentQuandoIdExiste() throws Exception {

        ResultActions resultado = mockMvc.perform(delete("/pagamentos/{id}", idExiste)
                .accept(MediaType.APPLICATION_JSON));
        resultado.andExpect(status().isNoContent());
        resultado.andDo(print());
    }

    @Test
    @DisplayName("delete Deve Retornar NotFound Quando Id Não Existe")
    public void deleteDeveRetornarNotFoundQuandoIdNaoExiste() throws Exception {

        ResultActions resultado = mockMvc.perform(delete("/pagamentos/{id}", idNaoExiste)
                .accept(MediaType.APPLICATION_JSON));
        resultado.andExpect(status().isNotFound());
        resultado.andDo(print());
    }



}
