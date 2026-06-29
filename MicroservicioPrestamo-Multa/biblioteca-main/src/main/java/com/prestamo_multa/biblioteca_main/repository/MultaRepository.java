package com.prestamo_multa.biblioteca_main.repository;

import com.prestamo_multa.biblioteca_main.model.Multa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MultaRepository extends JpaRepository<Multa, Integer> {
    List<Multa> findByUsuarioId(Integer usuarioId);
}