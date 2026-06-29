package com.prestamo_multa.biblioteca_main.DTO;

import com.prestamo_multa.biblioteca_main.model.EstadoMulta;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class MultaDTO {
    private Integer id;
    private Integer usuarioId;
    private Integer prestamoId;
    private Double monto;
    private LocalDate fechaGenerada;
    private LocalDate fechaPago;
    private EstadoMulta estado;
}