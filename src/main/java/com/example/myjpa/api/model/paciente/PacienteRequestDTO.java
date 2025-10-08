package com.example.myjpa.api.model.paciente;

import com.example.myjpa.api.enums.Sexo;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;

import java.util.Date;

public record PacienteRequestDTO(
        @NotNull(message = "O nome não pode ser nulo !")
        @Pattern(regexp = "^[\\p{L} ']+$", message = "O nome informado é inválido !")
        String nome,
        @Pattern(regexp = "^\\d{15}$", message = "O cns informado não é válido !")
        String cns,
        @PastOrPresent
        @NotNull
        Date nascimento,
        @NotNull
        @Min(1)
        int numero_filhos,
        @Enumerated(EnumType.STRING)
        Sexo sexo
) {
}
