package com.example.MicroservicioLibro.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.MicroservicioLibro.DTO.IdiomaDTO;
import com.example.MicroservicioLibro.model.Idioma;
import com.example.MicroservicioLibro.model.Libro;
import com.example.MicroservicioLibro.repository.IdiomaRepository;
import com.example.MicroservicioLibro.repository.LibroRepository; // Importamos para verificar integridad

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class IdiomaService {

    @Autowired
    private IdiomaRepository idiomaRepository;

    @Autowired
    private LibroRepository libroRepository;

    public List<IdiomaDTO> obtenerTodos() {
        log.info("Service: Listando todos los idiomas disponibles");
        return idiomaRepository.findAll().stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    public IdiomaDTO buscarPorId(Integer id) {
        log.info("Service: Buscando idioma con ID: {}", id);
        Idioma idioma = idiomaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Error: El idioma con ID " + id + " no existe."));
        return convertirADTO(idioma);
    }

    public IdiomaDTO guardar(IdiomaDTO dto) {
        log.info("Service: Guardando nuevo idioma: {}", dto.getNombreIdioma());
        Idioma idioma = Idioma.builder()
            .nombreIdioma(dto.getNombreIdioma())
            .build();
        return convertirADTO(idiomaRepository.save(idioma));
    }

    public IdiomaDTO actualizar(Integer id, IdiomaDTO dto) {
        log.info("Service: Actualizando idioma con ID: {}", id);
        Idioma idioma = idiomaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se puede actualizar, el idioma con ID " + id + " no existe."));
        
        if (dto.getNombreIdioma() != null) {
            idioma.setNombreIdioma(dto.getNombreIdioma());
        }
        return convertirADTO(idiomaRepository.save(idioma));
    }

    public String eliminar(Integer id) {
    log.warn("Service: Intentando eliminar idioma con ID: {}", id);
    Idioma idioma = idiomaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("¡Imposible eliminar! El idioma con ID " + id + " no existe."));
    List<Libro> librosRelacionados = libroRepository.findByIdiomaId(id);
    if (!librosRelacionados.isEmpty()) {
        throw new RuntimeException("No se puede eliminar el idioma porque tiene " + librosRelacionados.size() + " libro(s) asociado(s).");
    }

    String nombre = idioma.getNombreIdioma();
    idiomaRepository.delete(idioma);
    return "El idioma '" + nombre + "' ha sido eliminado exitosamente.";
}

    private IdiomaDTO convertirADTO(Idioma idioma) {
        return IdiomaDTO.builder()
            .id(idioma.getId())
            .nombreIdioma(idioma.getNombreIdioma())
            .build();
    }
}