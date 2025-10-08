package com.example.myjpa.api.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClienteRequestDTO {
    // O nome é obrigatório e deve ter entre 3 e 100 caracteres.
    @NotBlank(message = "O nome do cliente é obrigatório.")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
    private String nome;

    // A lista de pedidos. Usamos @Valid para validar CADA item da lista.
    @Valid
    @NotNull(message = "A lista de pedidos é obrigatória.")
    private List<PedidoRequestDTO> pedidos;

    // Construtor, Getters e Setters...
    public ClienteRequestDTO() {}

    // ... (Implemente getters e setters)
}
