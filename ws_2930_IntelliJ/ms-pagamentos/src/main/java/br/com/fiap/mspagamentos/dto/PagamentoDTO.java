package br.com.fiap.mspagamentos.dto;

import br.com.fiap.mspagamentos.model.Pagamento;
import br.com.fiap.mspagamentos.model.Status;

import java.math.BigDecimal;
import java.util.Objects;

public class PagamentoDTO {

    private Long id;
    private BigDecimal valor;
    private String nome; //nome
    private String numeroDoCartao; //número
    private String validade; //data de validade
    private String codigo; //cod. segurança
    private Status status;
    private Long pedidoId;
    private Long formaDePagamentoId; // 1 - dinheiro || 2 - cartão

    public PagamentoDTO() {
    }

    public PagamentoDTO(Long id, BigDecimal valor,
                        String nome, String numeroDoCartao,
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
        this.id = entity.getId();
        this.valor = entity.getValor();
        this.nome = entity.getNome();
        this.numeroDoCartao = entity.getNumeroDoCartao();
        this.validade = entity.getValidade();
        this.codigo = entity.getCodigo();
        this.status = entity.getStatus();
        this.pedidoId = getPedidoId();
        this.formaDePagamentoId = getFormaDePagamentoId();
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PagamentoDTO that = (PagamentoDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
