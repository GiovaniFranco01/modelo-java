package com.example.myjpa.api.controller;

import com.example.myjpa.api.model.paciente.Paciente;
import com.example.myjpa.api.model.paciente.PacienteRequestDTO;
import com.example.myjpa.api.service.PacienteService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PacienteController {
    @Autowired
    private PacienteService pacienteService;


    // Ver a classe ValidationExceptionHandler do pacote "infra" !!
    @PostMapping("/paciente")
    public ResponseEntity<String> Cadastrar(@RequestBody @Valid PacienteRequestDTO dto){

        System.out.println(dto);
        Paciente paciente = new Paciente();
        BeanUtils.copyProperties(dto,paciente);
        this.pacienteService.save(paciente);
        return ResponseEntity.ok().body("ok, deu bom !!");
    }
}
