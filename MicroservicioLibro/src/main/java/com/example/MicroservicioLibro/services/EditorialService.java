package com.example.MicroservicioLibro.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.MicroservicioLibro.DTO.EditorialDTO;
import com.example.MicroservicioLibro.model.Editorial;
import com.example.MicroservicioLibro.repository.EditorialRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class EditorialService {

    @Autowired
    private EditorialRepository editorialRepository;

    public List<EditorialDTO> obtenerTodas() {
        log.info("Service: Listando todas las editoriales");
        return editorialRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public EditorialDTO buscarPorId(Integer id) {
        Editorial editorial = editorialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Editorial no encontrada con ID: " + id));
        return mapToDTO(editorial);
    }

    public EditorialDTO guardar(EditorialDTO dto) {
        log.info("Service: Registrando editorial: {}", dto.getNombre());
        Editorial editorial = Editorial.builder()
                .nombre(dto.getNombre())
                .direccion(dto.getDireccion())
                .telefono(dto.getTelefono())
                .correoContacto(dto.getCorreoContacto())
                .build();
        return mapToDTO(editorialRepository.save(editorial));
    }

    public EditorialDTO actualizar(Integer id, EditorialDTO dto) {
        log.info("Service: Actualizando editorial con ID: {}", id);
        Editorial editorial = editorialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se puede actualizar, editorial no encontrada con ID: " + id));
        
        // Actualización segura: solo cambia los campos si no son nulos
        if (dto.getNombre() != null) editorial.setNombre(dto.getNombre());
        if (dto.getDireccion() != null) editorial.setDireccion(dto.getDireccion());
        if (dto.getTelefono() != null) editorial.setTelefono(dto.getTelefono());
        if (dto.getCorreoContacto() != null) editorial.setCorreoContacto(dto.getCorreoContacto());
        
        return mapToDTO(editorialRepository.save(editorial));
    }

    public String eliminar(Integer id) {
        log.warn("Service: Intentando eliminar editorial con ID: {}", id);
        if (!editorialRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar, la editorial con ID " + id + " no existe");
        }
        editorialRepository.deleteById(id);
        return "Editorial eliminada correctamente";
    }

    private EditorialDTO mapToDTO(Editorial e) {
        return EditorialDTO.builder()
                .id(e.getId())
                .nombre(e.getNombre())
                .direccion(e.getDireccion())
                .telefono(e.getTelefono())
                .correoContacto(e.getCorreoContacto())
                .build();
    }
}