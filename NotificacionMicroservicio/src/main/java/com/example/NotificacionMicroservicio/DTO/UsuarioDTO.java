package com.example.NotificacionMicroservicio.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Integer id;
    private String nombre;
    private String correo;
    private String estadoNotificaciones;
    private Integer numero;
    private List<Integer> librosIds; 
    private List<String> nombresLibros;
}
