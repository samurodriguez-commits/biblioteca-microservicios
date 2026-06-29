package com.prestamo_multa.biblioteca_main.DTO;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class MultaRequestDTO {

    @NotNull(message = "El ID de usuario es obligatorio")
    private Integer usuarioId;

    @NotNull(message = "El ID del préstamo es obligatorio")
    private Integer prestamoId;

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser mayor a cero")
    private Double monto;
}