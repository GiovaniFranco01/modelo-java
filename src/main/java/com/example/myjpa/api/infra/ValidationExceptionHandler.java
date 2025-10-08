package com.example.myjpa.api.infra;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
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

    @ExceptionHandler({MismatchedInputException.class, InvalidFormatException.class})
    public ResponseEntity<Map<String, String>> handleTypeMismatchErrors(Exception ex) {

        String fieldName = "Campo(s) com erro de tipo";
        String detalheErro = "Formato de valor inválido. Verifique se a data ou o Enum estão corretos.";

        // Tentativa de extrair o campo específico (funciona na maioria dos casos do Jackson)
        if (ex instanceof MismatchedInputException) {
            MismatchedInputException mie = (MismatchedInputException) ex;
            if (mie.getPath() != null && !mie.getPath().isEmpty()) {
                fieldName = mie.getPath().get(mie.getPath().size() - 1).getFieldName();
            }
        }

        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put(fieldName, detalheErro);
        errorResponse.put("erro_detalhe", ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
