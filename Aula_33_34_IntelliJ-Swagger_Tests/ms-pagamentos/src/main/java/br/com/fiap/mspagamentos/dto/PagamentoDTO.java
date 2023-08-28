package br.com.fiap.mspagamentos.dto;

import br.com.fiap.mspagamentos.model.Pagamento;
import br.com.fiap.mspagamentos.model.Status;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class PagamentoDTO {

    private Long id;

    @NotNull(message = "Campo requerido")
    @Positive(message = "O valor deve ser um número positivo")
    private BigDecimal valor;

    private String nome; //nome
    private String numeroDoCartao; //número
    private String validade; //data de validade
    private String codigo; //cod. segurança
    private Status status;

    @NotNull(message = "Campo requerido")
    @Positive
    private Long pedidoId;

    @NotNull(message = "Campo requerido")
    @Positive
    private Long formaDePagamentoId; // 1 - dinheiro || 2 - cartão

    public PagamentoDTO() { }

    public PagamentoDTO(Long id, BigDecimal valor, String nome, String numeroDoCartao,
                        String validade, String codigo, Status status,
                        Long pedidoId, Long formaDePagamentoId) {
        this.id = id;
        this.valor = valor;
        this.nome = nome;
        this.numeroDoCartao = numeroDoCartao;
        this.validade = validade;
        this.codigo = codigo;
        this.status = status;
        this.pedidoId = pedidoId;
        this.formaDePagamentoId = formaDePagamentoId;
    }

    public PagamentoDTO(Pagamento entity) {
        id = entity.getId();
        valor = entity.getValor();
        nome = entity.getNome();
        numeroDoCartao = entity.getNumeroDoCartao();
        validade = entity.getValidade();
        codigo = entity.getCodigo();
        status = entity.getStatus();
        pedidoId = entity.getPedidoId();
        formaDePagamentoId = entity.getFormaDePagamentoId();
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getNome() {
        return nome;
    }

    public String getNumeroDoCartao() {
        return numeroDoCartao;
    }

    public String getValidade() {
        return validade;
    }

    public String getCodigo() {
        return codigo;
    }

    public Status getStatus() {
        return status;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public Long getFormaDePagamentoId() {
        return formaDePagamentoId;
    }

}
