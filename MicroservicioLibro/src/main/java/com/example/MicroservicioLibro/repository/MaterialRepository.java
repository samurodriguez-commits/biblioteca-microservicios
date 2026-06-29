package com.example.MicroservicioLibro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.MicroservicioLibro.model.Material;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {
}