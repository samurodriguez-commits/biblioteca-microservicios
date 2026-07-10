package com.example.NotificacionMicroservicio.DTO;

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
    private String autor;
    private String isbn;
    private String categoria;
    private Integer cantidad;
}
