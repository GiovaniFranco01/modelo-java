package com.example.myjpa.api.service;

import com.example.myjpa.api.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PacienteService {


    @Autowired
    public PacienteRepository repository;
}
