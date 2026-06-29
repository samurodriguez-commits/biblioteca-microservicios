package com.example.MicroservicioLibro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.MicroservicioLibro.model.Libro;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Integer> {
    List<Libro> findByNombreContainingIgnoreCase(String nombre);
    List<Libro> findByIdiomaId(Integer idiomaId);
}
