package com.example.NotificacionMicroservicio.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Table(name = "notificaciones") 
@Data 
@Builder 
@AllArgsConstructor 
@NoArgsConstructor 
public class Notificacion {

    @Id // Define este campo como la Llave Primaria de la tabla
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false) 
    private Integer usuarioId;

    @Column(nullable = false)
    private Integer libroId;

    @Column(nullable = false)
    private String mensaje;

    @Column(nullable = false)
    private LocalDate fechaEnvio;

    @Column(nullable = false)
    private Boolean leida; // Para saber si el usuario ya vio el aviso

    @Enumerated(EnumType.STRING) // Guarda el Enum como texto "ALERTA", "MULTA" y no como un número
    @Column(nullable = false)
    private Tipo_Notificacion tipo;
}