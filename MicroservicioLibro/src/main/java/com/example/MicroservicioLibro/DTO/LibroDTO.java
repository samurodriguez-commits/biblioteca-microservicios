package com.example.MicroservicioLibro.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LibroDTO {
    private Integer id;
    private String nombre;
    private String fechaPublicacion;
    private String nombreAutor;
    private String nombreEditorial;
    private String idioma; 
    private Integer autorId;
    private Integer editorialId;
    private Integer categoriaId;
    private Integer idiomaId;
    private String estado; // Ej: "Disponible", "Prestado", "En Reserva"
}

