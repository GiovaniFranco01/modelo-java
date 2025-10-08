package com.example.myjpa.api.repository;

import com.example.myjpa.api.model.temp.Assento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssentoRepository extends JpaRepository<Assento, Long> {
}
