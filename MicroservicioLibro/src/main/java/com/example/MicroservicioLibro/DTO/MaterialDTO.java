package com.example.MicroservicioLibro.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder 
@NoArgsConstructor
@AllArgsConstructor
public class MaterialDTO {
    private Integer id;
    private String nombreMaterial;
    private String nombreLibro;
    private String tipoMaterial; 
    private String estado;
    private Integer libroId;       // Ej: Disponible, En mantenimiento
}