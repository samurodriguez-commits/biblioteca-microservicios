package com.example.MicroservicioLibro.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.MicroservicioLibro.model.Autor;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Integer> {
    Optional<Autor> findByRut(String rut); // Opcional: Para validar duplicados antes de guardar
}