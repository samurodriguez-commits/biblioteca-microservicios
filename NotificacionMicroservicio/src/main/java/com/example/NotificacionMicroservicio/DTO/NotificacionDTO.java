package com.example.NotificacionMicroservicio.DTO;

import java.time.LocalDate;

import com.example.NotificacionMicroservicio.model.Tipo_Notificacion;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificacionDTO {

    private Integer id;
    private Integer usuarioId;
    private String nombreUsuario;
    private Integer libroId;
    private String nombreLibro;
    private String mensaje;
    private LocalDate fechaEnvio;
    private Boolean leida;
    private Tipo_Notificacion tipo;
}