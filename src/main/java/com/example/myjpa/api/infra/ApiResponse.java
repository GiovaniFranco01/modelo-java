package com.example.myjpa.api.infra;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Getter
public class ApiResponse<T> {

    private final int status;
    private final boolean success;
    private final T data; // O payload real (ex: Paciente ou List<Paciente>)
    private final List<String> messages; // Mensagens gerais (sucesso, erro simples)
    private final Map<String, String> errors; // Erros detalhados de campo (para @Valid)
    private final LocalDateTime timestamp;

    // --- Construtor Privado (Força o uso dos métodos estáticos) ---
    private ApiResponse(int status, boolean success, T data, List<String> messages, Map<String, String> errors) {
        this.status = status;
        this.success = success;
        this.data = data;
        this.messages = messages;
        this.errors = errors;
        this.timestamp = LocalDateTime.now();
    }

    // --- MÉTODOS ESTÁTICOS DE SUCESSO ---

    /** Retorna sucesso com dados (payload) e uma única mensagem. */
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(
            HttpStatus.OK.value(),
            true,
            data,
            Collections.singletonList(message), // Lista de uma única mensagem
            null // Não há erros de campo no sucesso
        );
    }

    /** Retorna sucesso SEM dados (útil para DELETE, por exemplo) e uma única mensagem. */
    public static ApiResponse<?> success(String message) {
        return new ApiResponse<>(
            HttpStatus.OK.value(),
            true,
            null,
            Collections.singletonList(message),
            null
        );
    }

    public static <T> ApiResponse<T> success(T data, String message, HttpStatus status) {
    return new ApiResponse<>(
        status.value(),
        true,
        data,
        Collections.singletonList(message),
        null
    );
}

    // --- MÉTODOS ESTÁTICOS DE FALHA ---

    /** Retorna falha para ERROS DE VALIDAÇÃO (@Valid). */
    public static ApiResponse<?> validationFailure(Map<String, String> fieldErrors) {
        return new ApiResponse<>(
            HttpStatus.BAD_REQUEST.value(),
            false,
            null,
            Collections.singletonList("Um ou mais campos falharam na validação."), // Mensagem geral
            fieldErrors // O Map de erros de campo
        );
    }

    /** Retorna falha para ERROS GERAIS/DE NEGÓCIO (Ex: CNS já cadastrado, 404 Not Found). */
    public static ApiResponse<?> failure(HttpStatus status, String message) {
        return new ApiResponse<>(
            status.value(),
            false,
            null,
            Collections.singletonList(message), // A única mensagem de erro
            null // Não há erros de campo detalhados
        );
    }


}
