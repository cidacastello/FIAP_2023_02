package br.com.fiap.mspagamentos.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ValidationCustomErrorDTO {

    private Instant timestamp;
    private Integer status;
    private String error;
    private String path;

    //listar os campos com erro
    private List<FieldMessageDTO> listFieldError = new ArrayList<>();

    public ValidationCustomErrorDTO(Instant timestamp, Integer status,
                                    String error, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.path = path;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getPath() {
        return path;
    }

    public List<FieldMessageDTO> getFieldMessageDTOS() {
        return listFieldError;
    }

    //método para add erros na List
    public void addError(String filedName, String message) {
        listFieldError.add(new FieldMessageDTO(filedName, message));
    }

}
