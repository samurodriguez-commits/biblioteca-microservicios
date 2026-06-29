package com.prestamo_multa.biblioteca_main.services;

import com.prestamo_multa.biblioteca_main.DTO.PrestamoDTO;
import com.prestamo_multa.biblioteca_main.DTO.PrestamoRequestDTO;
import com.prestamo_multa.biblioteca_main.model.EstadoPrestamo;
import com.prestamo_multa.biblioteca_main.model.Prestamo;
import com.prestamo_multa.biblioteca_main.repository.PrestamoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;

    PrestamoService(PrestamoRepository prestamoRepository) {
        this.prestamoRepository = prestamoRepository;
    }

    public List<PrestamoDTO> obtenerTodos() {
        log.info("Consultando todos los préstamos");
        return prestamoRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public PrestamoDTO buscarPorId(Integer id) {
        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado con ID: " + id));
        return convertirADTO(prestamo);
    }

    public List<PrestamoDTO> listarPorUsuario(Integer usuarioId) {
        return prestamoRepository.findByUsuarioId(usuarioId).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public PrestamoDTO crearPrestamo(PrestamoRequestDTO request) {
        log.info("Creando préstamo para usuario ID: {}", request.getUsuarioId());
        Prestamo prestamo = Prestamo.builder()
                .usuarioId(request.getUsuarioId())
                .libroId(request.getLibroId())
                .fechaPrestamo(LocalDate.now())
                .fechaDevolucion(request.getFechaDevolucion())
                .estado(EstadoPrestamo.ACTIVO)
                .build();
        return convertirADTO(prestamoRepository.save(prestamo));
    }

    public PrestamoDTO actualizarEstado(Integer id, EstadoPrestamo nuevoEstado) {
        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado con ID: " + id));
        prestamo.setEstado(nuevoEstado);
        if (nuevoEstado == EstadoPrestamo.DEVUELTO) {
            prestamo.setFechaDevolucionReal(LocalDate.now());
        }
        return convertirADTO(prestamoRepository.save(prestamo));
    }

    public void eliminar(Integer id) {
        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado con ID: " + id));
        prestamoRepository.delete(prestamo);
    }

    private PrestamoDTO convertirADTO(Prestamo p) {
        return PrestamoDTO.builder()
                .id(p.getId())
                .usuarioId(p.getUsuarioId())
                .libroId(p.getLibroId())
                .fechaPrestamo(p.getFechaPrestamo())
                .fechaDevolucion(p.getFechaDevolucion())
                .fechaDevolucion(p.getFechaDevolucionReal())
                .estado(p.getEstado())
                .build();
    }
}