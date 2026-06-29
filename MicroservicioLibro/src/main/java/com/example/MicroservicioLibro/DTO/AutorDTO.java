package com.example.MicroservicioLibro.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AutorDTO {
    private Integer id;
    private String rut;
    private String correo;
    private String nombre;
    private String numero; // Consistente con la Entidad (String)
    private String ubicacion;
    private String nacionalidad;
}
