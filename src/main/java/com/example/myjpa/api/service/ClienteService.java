package com.example.myjpa.api.service;

import com.example.myjpa.api.model.temp.Cliente;
import com.example.myjpa.api.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente salvar(Cliente cliente){
        return this.clienteRepository.save(cliente);
    }
}
