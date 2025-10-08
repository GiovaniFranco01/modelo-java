package com.example.myjpa.api.enums;

public enum Sexo {
    MASCULINO("Masc"),
    FEMININO("Fem");

    final String sexo;
    Sexo(String sexo){
        this.sexo = sexo;
    }
}
