package br.com.fiap.mspagamentos.dto;

public class FieldMessageDTO {

    private String fieldName;
    private String message;

    public FieldMessageDTO(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }

    // código omitido

    public String getFieldName() {
        return fieldName;
    }

    public String getMessage() {
        return message;
    }
}
