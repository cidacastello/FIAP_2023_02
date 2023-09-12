package br.com.fiap.mspagamentos.tests;

import br.com.fiap.mspagamentos.dto.PagamentoDTO;
import br.com.fiap.mspagamentos.model.Pagamento;
import br.com.fiap.mspagamentos.model.Status;

import java.math.BigDecimal;

//Classe para Instanciar objetos
public class Factory {

    //código omitido
    public static Pagamento createPagamento() {

        Pagamento pagamento = new Pagamento(1L, BigDecimal.valueOf(32.25), "Bach", "322345698",
                "07/25", "547", Status.CRIADO, 1L, 2L);
        return pagamento;
    }

    //acrescentar
    public static PagamentoDTO createPagamentoDTO(){
        Pagamento pagamento = createPagamento();
        return new PagamentoDTO(pagamento);
    }
}
