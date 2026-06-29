package com.prestamo_multa.biblioteca_main.repository;

import com.prestamo_multa.biblioteca_main.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Integer> {
    List<Prestamo> findByUsuarioId(Integer usuarioId);
}