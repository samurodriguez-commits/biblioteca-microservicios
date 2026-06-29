package com.example.NotificacionMicroservicio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.NotificacionMicroservicio.model.Notificacion;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Integer> {
    List<Notificacion> findByUsuarioId(Integer usuarioId); 
    List<Notificacion> findByUsuarioIdAndLeidaFalse(Integer usuarioId); 
}