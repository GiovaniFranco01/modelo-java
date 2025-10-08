package com.example.myjpa.api.model.paciente;

import com.example.myjpa.api.enums.Sexo;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
@NoArgsConstructor
@Getter
@Setter
@Data
@Entity
public class Paciente implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String cns;
    private Date nascimento;
    @Column(columnDefinition = "SMALLINT")
    private int numero_filhos;
    private Sexo sexo;
}
