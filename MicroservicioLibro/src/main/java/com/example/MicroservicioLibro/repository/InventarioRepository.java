package com.example.MicroservicioLibro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.MicroservicioLibro.model.Inventario;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Integer> {
}