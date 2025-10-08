package com.example.myjpa.api.service;

import com.example.myjpa.api.model.paciente.CnsJaCadastradoException;
import com.example.myjpa.api.model.paciente.Paciente;
import com.example.myjpa.api.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PacienteService {


    @Autowired
    public PacienteRepository repository;

    public Paciente save(Paciente paciente) {
        try {

        return this.repository.save(paciente);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage() != null && e.getMessage().contains("cns")) {
        throw new CnsJaCadastradoException("O CNS informado já está cadastrado no sistema.");
    }
    throw e;
        }

    }
}
