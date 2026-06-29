package com.prestamo_multa.biblioteca_main.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "multas")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Multa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer usuarioId;

    @Column(nullable = false)
    private Integer prestamoId;

    @Column(nullable = false)
    private Double monto;

    @Column(nullable = false)
    private LocalDate fechaGenerada;

    private LocalDate fechaPago;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoMulta estado;
}