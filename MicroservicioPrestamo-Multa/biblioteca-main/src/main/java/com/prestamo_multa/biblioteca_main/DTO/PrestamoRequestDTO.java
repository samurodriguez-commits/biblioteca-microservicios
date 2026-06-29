package com.prestamo_multa.biblioteca_main.DTO;

import java.time.LocalDate;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrestamoRequestDTO {

    @NotNull(message = "El ID de usuario es obligatorio")
    private Integer usuarioId;

    @NotNull(message = "El ID de libro es obligatorio")
    private Integer libroId;

    @NotNull(message = "La fecha de devolución es obligatoria")
    @Future(message = "La fecha de devolución debe ser una fecha futura")
    private LocalDate fechaDevolucion;
}