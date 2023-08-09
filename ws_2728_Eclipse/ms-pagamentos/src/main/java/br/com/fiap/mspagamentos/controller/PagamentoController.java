package br.com.fiap.mspagamentos.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {
	
	@GetMapping
	public String getOi() {
		
		return "Oi";
		
	}

}


