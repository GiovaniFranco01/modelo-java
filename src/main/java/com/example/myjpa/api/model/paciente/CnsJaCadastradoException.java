package com.example.myjpa.api.model.paciente;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CnsJaCadastradoException extends RuntimeException{
    public CnsJaCadastradoException(String message) {
        super(message);
    }
}
