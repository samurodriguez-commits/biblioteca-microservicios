package com.example.MicroservicioLibro.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.MicroservicioLibro.model.Editorial;

@Repository
public interface EditorialRepository extends JpaRepository<Editorial, Integer> {
    Optional<Editorial> findByNombre(String nombre); // Para buscar editoriales por nombre si es necesario
}
