package br.com.fiap.mspagamentos.service.exception;

public class DatabaseException extends RuntimeException {

    public DatabaseException(String message) {
        super(message);
    }
}
