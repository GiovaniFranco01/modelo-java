package com.example.myjpa.api.enums;

import lombok.Getter;

@Getter
public enum Sexo {
    MASCULINO("Masc"),
    FEMININO("Fem");

    final String sexo;
    Sexo(String sexo){
        this.sexo = sexo;
    }


}
