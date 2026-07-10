package com.example.NotificacionMicroservicio.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.NotificacionMicroservicio.DTO.NotificacionRequestDTO;
import com.example.NotificacionMicroservicio.DTO.NotificacionResponseDTO;
import com.example.NotificacionMicroservicio.DTO.UsuarioDTO;
import com.example.NotificacionMicroservicio.model.Notificacion;
import com.example.NotificacionMicroservicio.repository.NotificacionRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class NotificacionService {
    @Autowired
    private NotificacionRepository notificacionRepository;
    @Autowired
    private UsuarioService usuarioService;

    private NotificacionResponseDTO mapToDTO(Notificacion notificacion) {
        String nombreUsuario = "Usuario no encontrado";
        try {
            if (notificacion.getUsuarioId() != null) {
                UsuarioDTO usuario = usuarioService.buscarPorId(notificacion.getUsuarioId());
                if (usuario != null) nombreUsuario = usuario.getNombre();
            }
        } catch (Exception e) {
            log.warn("No se pudo obtener usuario ID {}", notificacion.getUsuarioId());
        }
        return NotificacionResponseDTO.builder()
            .id(notificacion.getId())
            .mensaje(notificacion.getMensaje())
            .tipo(notificacion.getTipo())
            .nombreUsuario(nombreUsuario)
            .fechaCreacion(notificacion.getFechaCreacion())
            .leido(notificacion.getLeido())
            .build();
    }

    public List<NotificacionResponseDTO> obtenerTodas() {
        return notificacionRepository.findAll().stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }

    public NotificacionResponseDTO buscarPorId(Integer id) {
        Notificacion notificacion = notificacionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Notificacion no encontrada"));
        return mapToDTO(notificacion);
    }

    public List<NotificacionResponseDTO> listarPorUsuario(Integer usuarioId) {
        return notificacionRepository.findByUsuarioId(usuarioId).stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }

    public NotificacionResponseDTO crearNotificacion(NotificacionRequestDTO request) {
        Notificacion notificacion = Notificacion.builder()
            .mensaje(request.getMensaje())
            .tipo(request.getTipo())
            .usuarioId(request.getUsuarioId())
            .fechaCreacion(java.time.LocalDateTime.now())
            .leido(false)
            .build();
        return mapToDTO(notificacionRepository.save(notificacion));
    }

    public NotificacionResponseDTO actualizarNotificacion(Integer id, NotificacionRequestDTO request) {
        Notificacion notificacion = notificacionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Notificacion no encontrada"));
        if (request.getMensaje() != null) notificacion.setMensaje(request.getMensaje());
        if (request.getTipo() != null) notificacion.setTipo(request.getTipo());
        if (request.getUsuarioId() != null) notificacion.setUsuarioId(request.getUsuarioId());
        return mapToDTO(notificacionRepository.save(notificacion));
    }

    public String eliminarNotificacion(Integer id) {
        Notificacion notificacion = notificacionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Notificacion no encontrada"));
        notificacionRepository.delete(notificacion);
        return "Notificacion eliminada";
    }
}
