package com.example.myjpa.api.infra;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

// Esta anotação garante que esta classe 'ouça' todas as exceções dos Controllers
@RestControllerAdvice
public class ValidationExceptionHandler {

    // Este método é chamado toda vez que um @Valid falha
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        // 1. Cria um mapa para armazenar os erros (campo -> mensagem)
        Map<String, String> errors = new HashMap<>();

        // 2. Itera sobre a lista de erros da exceção
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();

            // 3. Popula o mapa com a chave (nome do campo) e o valor (a mensagem de erro)
            errors.put(fieldName, errorMessage);
        });

        // 4. Retorna a resposta padronizada com o status 400 Bad Request
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
