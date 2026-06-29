package com.example.MicroservicioLibro.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.MicroservicioLibro.DTO.AutorDTO;
import com.example.MicroservicioLibro.DTO.EditorialDTO;
import com.example.MicroservicioLibro.DTO.LibroDTO;
import com.example.MicroservicioLibro.model.Libro;
import com.example.MicroservicioLibro.repository.LibroRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class LibroService {
    
    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorService autorService;

    @Autowired
    private EditorialService editorialService;

    private LibroDTO convertirADTO(Libro libro) {
        AutorDTO autor = autorService.buscarPorId(libro.getAutorId());
        EditorialDTO editorial = editorialService.buscarPorId(libro.getEditorialId());

        return LibroDTO.builder()
                .id(libro.getId())
                .nombre(libro.getNombre())
                .fechaPublicacion(libro.getFechaPublicacion())
                .idiomaId(libro.getIdiomaId())
                .autorId(libro.getAutorId())
                .nombreAutor(autor != null ? autor.getNombre() : "Autor desconocido")
                .editorialId(libro.getEditorialId())
                .nombreEditorial(editorial != null ? editorial.getNombre() : "Editorial desconocida")
                .categoriaId(libro.getCategoriaId())
                .build();
    }

    public List<LibroDTO> obtenerTodos() {
        return libroRepository.findAll().stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    public LibroDTO buscarPorId(Integer id) {
        Libro libro = libroRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("El libro con ID " + id + " no existe."));
        return convertirADTO(libro);
    }

    public LibroDTO guardar(LibroDTO dto) {
        Libro libro = Libro.builder()
            .nombre(dto.getNombre())
            .fechaPublicacion(dto.getFechaPublicacion())
            .idiomaId(dto.getIdiomaId())
            .autorId(dto.getAutorId())
            .editorialId(dto.getEditorialId())
            .categoriaId(dto.getCategoriaId())
            .build();
        return convertirADTO(libroRepository.save(libro));
    }

    public LibroDTO actualizar(Integer id, LibroDTO dto) {
        Libro libro = libroRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se puede actualizar: Libro no encontrado"));

        if (dto.getNombre() != null) libro.setNombre(dto.getNombre());
        if (dto.getFechaPublicacion() != null) libro.setFechaPublicacion(dto.getFechaPublicacion());
        if (dto.getIdiomaId() != null) libro.setIdiomaId(dto.getIdiomaId());
        if (dto.getAutorId() != null) libro.setAutorId(dto.getAutorId());
        if (dto.getEditorialId() != null) libro.setEditorialId(dto.getEditorialId()); 
        
        return convertirADTO(libroRepository.save(libro));
    }

    public String eliminar(Integer id) {
        Libro libro = libroRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No existe el libro"));
        libroRepository.delete(libro);
        return "Eliminado correctamente";
    }
}