package com.example.myjpa.api.service;

import com.example.myjpa.api.model.temp.Assento;
import com.example.myjpa.api.repository.AssentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssentoService {

    private AssentoRepository assentoRepository;

    public Assento salvar(Assento assento){
        return this.assentoRepository.save(assento);
    }
}
