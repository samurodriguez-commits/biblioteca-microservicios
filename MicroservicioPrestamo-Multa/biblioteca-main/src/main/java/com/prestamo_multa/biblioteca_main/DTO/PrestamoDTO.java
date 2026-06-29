package com.prestamo_multa.biblioteca_main.DTO;

import com.prestamo_multa.biblioteca_main.model.EstadoPrestamo;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class PrestamoDTO {
    private Integer id;
    private Integer usuarioId;
    private Integer libroId;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
    private EstadoPrestamo estado;
}