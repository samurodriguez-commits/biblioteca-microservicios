package com.example.MicroservicioLibro.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Data
@Builder 
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "materiales")
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Es obligatorio ingresar el nombre del material")
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String nombreMaterial;

    @Column(name = "tipo_material")
    private String tipoMaterial;

    @Column(name = "estado")
    private String estado;

    @Column(name = "libro_id")
    private Integer libroId;
}
