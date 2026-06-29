package com.prestamo_multa.biblioteca_main.services;

import com.prestamo_multa.biblioteca_main.DTO.MultaDTO;
import com.prestamo_multa.biblioteca_main.DTO.MultaRequestDTO;
import com.prestamo_multa.biblioteca_main.model.EstadoMulta;
import com.prestamo_multa.biblioteca_main.model.Multa;
import com.prestamo_multa.biblioteca_main.repository.MultaRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class MultaService {

    private final MultaRepository multaRepository;

    MultaService(MultaRepository multaRepository) {
        this.multaRepository = multaRepository;
    }

    public List<MultaDTO> obtenerTodas() {
        log.info("Consultando todas las multas");
        return multaRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public MultaDTO buscarPorId(Integer id) {
        Multa multa = multaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Multa no encontrada con ID: " + id));
        return convertirADTO(multa);
    }

    public List<MultaDTO> listarPorUsuario(Integer usuarioId) {
        return multaRepository.findByUsuarioId(usuarioId).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public MultaDTO crearMulta(MultaRequestDTO request) {
        Multa nueva = Multa.builder()
                .usuarioId(request.getUsuarioId())
                .prestamoId(request.getPrestamoId())
                .monto(request.getMonto())
                .fechaGenerada(LocalDate.now())
                .estado(EstadoMulta.PENDIENTE)
                .build();
        return convertirADTO(multaRepository.save(nueva));
    }

    public MultaDTO pagarMulta(Integer id) {
        Multa multa = multaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Multa no encontrada"));
        multa.setEstado(EstadoMulta.PAGADA);
        return convertirADTO(multaRepository.save(multa));
    }

    private MultaDTO convertirADTO(Multa multa) {
        return MultaDTO.builder()
                .id(multa.getId())
                .usuarioId(multa.getUsuarioId())
                .prestamoId(multa.getPrestamoId())
                .monto(multa.getMonto())
                .fechaGenerada(multa.getFechaGenerada())
                .estado(multa.getEstado())
                .build();
    }
}