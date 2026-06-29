package com.example.MicroservicioLibro.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditorialDTO {
    private Integer id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String correoContacto;
}
