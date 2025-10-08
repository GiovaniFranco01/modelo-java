package com.example.myjpa.api.controller;

import com.example.myjpa.api.infra.ApiResponse;
import com.example.myjpa.api.model.paciente.Paciente;
import com.example.myjpa.api.model.paciente.PacienteRequestDTO;
import com.example.myjpa.api.service.PacienteService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PacienteController {
    @Autowired
    private PacienteService pacienteService;


    // Ver a classe ValidationExceptionHandler do pacote "infra" !!
    @PostMapping(value = "/paciente")
    public ResponseEntity<ApiResponse<Paciente>> cadastrarPaciente(@Valid @RequestBody PacienteRequestDTO dto) {
        Paciente paciente = new Paciente();
        BeanUtils.copyProperties(dto, paciente);
        Paciente pacienteSalvo = this.pacienteService.save(paciente);
        ApiResponse<Paciente> responseBody = ApiResponse.success(
                pacienteSalvo,
                "Paciente cadastrado com sucesso!", HttpStatus.CREATED
        );
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

}
