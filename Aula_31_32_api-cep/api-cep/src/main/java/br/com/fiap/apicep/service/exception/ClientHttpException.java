package br.com.fiap.apicep.service.exception;

public class ClientHttpException extends RuntimeException {
    public ClientHttpException(String message) {

        super(message);
    }
}
