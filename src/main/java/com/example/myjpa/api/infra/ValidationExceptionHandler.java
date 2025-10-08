package com.example.myjpa.api.infra;

import com.example.myjpa.api.model.paciente.CnsJaCadastradoException;
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
    public ResponseEntity<ApiResponse<?>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        // 1. Coleta e mapeia todos os erros de campo
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        });

        // 2. Usa o construtor validationFailure() para enviar a lista de erros no Map 'errors'
        ApiResponse<?> responseBody = ApiResponse.validationFailure(fieldErrors);

        // Retorna 400 Bad Request
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MismatchedInputException.class, InvalidFormatException.class})
    public ResponseEntity<ApiResponse<?>> handleTypeMismatchErrors(Exception ex) {

        String fieldName = "dado_geral"; // Nome de campo padrão caso não encontre
        String defaultMessage = "Formato de valor inválido. Verifique se o tipo de dado (data, número, ou seleção) está correto.";

        // 1. Tenta extrair o campo específico que causou o erro (Data/Enum)
        if (ex instanceof MismatchedInputException) {
            MismatchedInputException mie = (MismatchedInputException) ex;
            if (mie.getPath() != null && !mie.getPath().isEmpty()) {
                // Pega o nome do campo da última parte do path JSON
                fieldName = mie.getPath().get(mie.getPath().size() - 1).getFieldName();
            }
        }

        // 2. Cria o Map de erros no formato Campo -> Mensagem
        Map<String, String> fieldErrors = new HashMap<>();

        // Usamos o campo e a mensagem padrão para o Map de erros
        fieldErrors.put(fieldName, defaultMessage);

        // 3. Cria o ApiResponse usando o método de falha de validação
        // É o mesmo método que usamos para @Valid, pois ambos são erros de dados de entrada (400)
        ApiResponse<?> responseBody = ApiResponse.validationFailure(fieldErrors);

        // 4. Retorna a resposta padronizada com o status 400 Bad Request
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CnsJaCadastradoException.class)
    public ResponseEntity<ApiResponse<?>> handleCnsJaCadastrado(
            CnsJaCadastradoException ex) {

        ApiResponse<?> response = ApiResponse.failure(HttpStatus.CONFLICT, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}
