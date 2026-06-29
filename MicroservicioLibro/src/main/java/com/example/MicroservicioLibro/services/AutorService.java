package com.example.MicroservicioLibro.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.MicroservicioLibro.DTO.AutorDTO;
import com.example.MicroservicioLibro.model.Autor;
import com.example.MicroservicioLibro.repository.AutorRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    public List<AutorDTO> obtenerTodos() {
        log.info("Service: Obteniendo lista de autores");
        return autorRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public AutorDTO buscarPorId(Integer id) {
        log.info("Service: Buscando autor con ID: {}", id);
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Autor no encontrado con ID: " + id));
        return mapToDTO(autor);
    }

    public AutorDTO guardar(AutorDTO dto) {
        log.info("Service: Guardando nuevo autor: {}", dto.getNombre());
        Autor autor = Autor.builder()
                .rut(dto.getRut())
                .correo(dto.getCorreo())
                .nombre(dto.getNombre())
                .numero(dto.getNumero())
                .ubicacion(dto.getUbicacion())
                .nacionalidad(dto.getNacionalidad())
                .build();
        return mapToDTO(autorRepository.save(autor));
    }

    public AutorDTO actualizar(Integer id, AutorDTO dto) {
        log.info("Service: Actualizando autor con ID: {}", id);
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se puede actualizar, autor no encontrado con ID: " + id));
        
        // Actualización condicional: Solo se actualiza si el valor no es nulo
        if (dto.getNombre() != null) autor.setNombre(dto.getNombre());
        if (dto.getRut() != null) autor.setRut(dto.getRut());
        if (dto.getCorreo() != null) autor.setCorreo(dto.getCorreo());
        if (dto.getNumero() != null) autor.setNumero(dto.getNumero());
        if (dto.getUbicacion() != null) autor.setUbicacion(dto.getUbicacion());
        if (dto.getNacionalidad() != null) autor.setNacionalidad(dto.getNacionalidad());
        
        return mapToDTO(autorRepository.save(autor));
    }

    public String eliminar(Integer id) {
        log.warn("Service: Intentando eliminar autor con ID: {}", id);
        if (!autorRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar, el autor no existe");
        }
        autorRepository.deleteById(id);
        return "Autor con ID " + id + " eliminado correctamente";
    }

    private AutorDTO mapToDTO(Autor a) {
        return AutorDTO.builder()
                .id(a.getId())
                .rut(a.getRut())
                .correo(a.getCorreo())
                .nombre(a.getNombre())
                .numero(a.getNumero())
                .ubicacion(a.getUbicacion())
                .nacionalidad(a.getNacionalidad())
                .build();
    }
}
