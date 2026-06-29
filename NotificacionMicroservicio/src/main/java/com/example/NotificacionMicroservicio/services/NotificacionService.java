package com.example.NotificacionMicroservicio.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.NotificacionMicroservicio.DTO.NotificacionDTO;
import com.example.NotificacionMicroservicio.DTO.NotificacionRequestDTO;
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
    private WebClient.Builder webClientBuilder;

    private NotificacionDTO mapToDTO(Notificacion n) {

    String nombreUsuario = "Usuario desconocido";
    String nombreLibro = "Libro desconocido";

    try {
        UsuarioDTO usuario = webClientBuilder.build()
                .get()
                .uri("http://localhost:8082/api/v1/usuarios/" + n.getUsuarioId())
                .retrieve()
                .bodyToMono(UsuarioDTO.class)
                .block();
        if (usuario != null) {
            nombreUsuario = usuario.getNombre();
        }
    } catch (Exception e) {
        log.warn(
            "No se pudo obtener usuario {}",
            n.getUsuarioId()
        );
    }
    try {
        LibroDTO libro = webClientBuilder.build()
                .get()
                .uri("http://localhost:8081/api/v1/libros/" + n.getLibroId())
                .retrieve()
                .bodyToMono(LibroDTO.class)
                .block();
        if (libro != null) {
            nombreLibro = libro.getNombre();
        }
    } catch (Exception e) {
        log.warn(
            "No se pudo obtener libro {}",
            n.getLibroId()
        );
    }
    return NotificacionDTO.builder()
            .id(n.getId())
            .usuarioId(n.getUsuarioId())
            .nombreUsuario(nombreUsuario)
            .libroId(n.getLibroId())
            .nombreLibro(nombreLibro)
            .mensaje(n.getMensaje())
            .fechaEnvio(n.getFechaEnvio())
            .leida(n.getLeida())
            .tipo(n.getTipo())
            .build();
}

    public NotificacionDTO buscarPorId(Integer id) {

        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Notificación no encontrada"));
        return mapToDTO(notificacion);
    }

    public List<NotificacionDTO> listarPorUsuario(Integer usuarioId) {

        return notificacionRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public NotificacionDTO crearNotificacion(NotificacionRequestDTO request) {

    Notificacion notificacion = Notificacion.builder()
            .usuarioId(request.getUsuarioId())
            .libroId(request.getLibroId())
            .mensaje(request.getMensaje())
            .tipo(request.getTipo())
            .fechaEnvio(LocalDate.now())
            .leida(false)
            .build();

    return mapToDTO(notificacionRepository.save(notificacion));
}

    public NotificacionDTO actualizarNotificacion(
            Integer id,
            NotificacionRequestDTO request) {

        Notificacion notif = notificacionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Notificación no encontrada"));

        notif.setMensaje(request.getMensaje());
        notif.setTipo(request.getTipo());

        return mapToDTO(notificacionRepository.save(notif));
    }

    public String eliminarNotificacion(Integer id) {

        if (!notificacionRepository.existsById(id)) {
            throw new RuntimeException(
                    "No se puede eliminar, notificación no encontrada");
        }

        notificacionRepository.deleteById(id);

        return "Notificación eliminada correctamente";
    }
}