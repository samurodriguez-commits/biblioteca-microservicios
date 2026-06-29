package com.example.NotificacionMicroservicio.DTO;

import com.example.NotificacionMicroservicio.model.Tipo_Notificacion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data 
public class NotificacionRequestDTO {

    @NotNull(message = "El ID de usuario es obligatorio") // Garantiza que el cliente envíe a quién va dirigida la notificación
    private Integer usuarioId;

    @NotBlank(message = "El mensaje no puede estar vacío") // Garantiza que el texto de la notificación exista y no sean puros espacios en blanco
    private String mensaje;

    @NotNull(message = "El Id del libro es obligatorio")
    private Integer libroId;
    
    @NotNull(message = "Debe especificar el tipo de notificación") // Garantiza que se seleccione una categoría de notificación (ej. SMS, EMAIL) válida
    private Tipo_Notificacion tipo;
}