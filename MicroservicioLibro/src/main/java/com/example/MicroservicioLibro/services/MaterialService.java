package com.example.MicroservicioLibro.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.MicroservicioLibro.DTO.LibroDTO;
import com.example.MicroservicioLibro.DTO.MaterialDTO;
import com.example.MicroservicioLibro.model.Material;
import com.example.MicroservicioLibro.repository.MaterialRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private LibroService libroService; 

    private MaterialDTO convertirADTO(Material m) {
        String nombreLibro = "Libro no asignado";
        
        try {
            if (m.getLibroId() != null) {
                LibroDTO libro = libroService.buscarPorId(m.getLibroId());
                if (libro != null) {
                    nombreLibro = libro.getNombre();
                }
            }
        } catch (Exception e) {
            log.warn("No se pudo obtener el libro ID {} para el material {}: {}", 
                     m.getLibroId(), m.getId(), e.getMessage());
            nombreLibro = "Error al obtener libro";
        }

        return MaterialDTO.builder()
            .id(m.getId())
            .nombreMaterial(m.getNombreMaterial())
            .tipoMaterial(m.getTipoMaterial())
            .estado(m.getEstado())
            .libroId(m.getLibroId())
            .nombreLibro(nombreLibro)
            .build();
    }

    public List<MaterialDTO> obtenerTodos() {
        log.info("Service: Listando todos los materiales");
        return materialRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public MaterialDTO guardar(MaterialDTO dto) {
        Material material = Material.builder()
            .nombreMaterial(dto.getNombreMaterial())
            .tipoMaterial(dto.getTipoMaterial())
            .estado(dto.getEstado())
            .libroId(dto.getLibroId())
            .build();
        return convertirADTO(materialRepository.save(material));
    }

    public MaterialDTO actualizar(Integer id, MaterialDTO dto) {
        Material m = materialRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Material no encontrado"));
        
        if (dto.getNombreMaterial() != null) m.setNombreMaterial(dto.getNombreMaterial());
        if (dto.getTipoMaterial() != null) m.setTipoMaterial(dto.getTipoMaterial());
        if (dto.getEstado() != null) m.setEstado(dto.getEstado());
        if (dto.getLibroId() != null) m.setLibroId(dto.getLibroId());
        
        return convertirADTO(materialRepository.save(m));
    }

    public String eliminar(Integer id) {
        Material m = materialRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No existe el material con ID " + id));
        String nombre = m.getNombreMaterial();
        materialRepository.delete(m);
        return "Material eliminado: " + nombre;
    }

    public MaterialDTO buscarPorId(Integer id) {
        Material m = materialRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Material no encontrado"));
        return convertirADTO(m);
    }
}
