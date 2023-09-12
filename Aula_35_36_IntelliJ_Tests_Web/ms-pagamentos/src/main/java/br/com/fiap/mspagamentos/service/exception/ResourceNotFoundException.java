package br.com.fiap.mspagamentos.service.exception;

public class ResourceNotFoundException extends RuntimeException {
    //RuntimeException não precisa de try/catch
    //Exceção customizada quando não encontrar um recurso
    public ResourceNotFoundException(String message) {
        super(message);
    }
}


