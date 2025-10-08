package com.example.myjpa.api.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PedidoRequestDTO {
    // O valor não pode ser nulo e deve ser positivo.
    @NotNull(message = "O valor total é obrigatório.")
    @DecimalMin(value = "0.01", message = "O valor total deve ser maior que zero.")
    private BigDecimal valor;

    // Construtor, Getters e Setters...
    public PedidoRequestDTO() {}

    // ... (Implemente getters e setters)
}
