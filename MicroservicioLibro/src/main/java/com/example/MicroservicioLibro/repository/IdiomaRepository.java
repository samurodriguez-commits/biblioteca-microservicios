package com.example.MicroservicioLibro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.MicroservicioLibro.model.Idioma;

@Repository
public interface IdiomaRepository extends JpaRepository<Idioma, Integer> {
    // Hereda todos los métodos CRUD (save, findAll, findById, etc.)
}