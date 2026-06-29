package com.example.NotificacionMicroservicio.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.NotificacionMicroservicio.DTO.NotificacionDTO;
import com.example.NotificacionMicroservicio.DTO.NotificacionRequestDTO;
import com.example.NotificacionMicroservicio.DTO.UsuarioDTO;
import com.example.NotificacionMicroservicio.model.Notificacion;
import com.example.NotificacionMicroservicio.model.Tipo_Notificacion;
import com.example.NotificacionMicroservicio.repository.NotificacionRepository;
import com.example.NotificacionMicroservicio.services.NotificacionService;
import com.example.NotificacionMicroservicio.services.UsuarioService;

@SpringBootTest
public class NotificacionServiceTest {

    @Autowired
    private NotificacionService notificacionService;

    @Mock
    private NotificacionRepository notificacionRepository;

    @Mock
    private UsuarioService usuarioService;

    private Notificacion createNotificacion() {
        return Notificacion.builder()
                .id(1)
                .usuarioId(10)
                .mensaje("Notificación para probar")
                .fechaEnvio(LocalDate.now())
                .leida(false)
                .tipo(Tipo_Notificacion.INFO)
                .build();
    }

    @Test
    public void testObtenerTodas() {
        Notificacion notif = createNotificacion();
        when(notificacionRepository.findAll()).thenReturn(List.of(notif));
        
        when(usuarioService.buscarPorId(10)).thenReturn(UsuarioDTO.builder().nombre("Pedrito Viagney").build());

        List<NotificacionDTO> lista = notificacionService.obtenerTodas();

        assertNotNull(lista);
        assertEquals(1, lista.size());
        assertEquals("Pedrito Viagney", lista.get(0).getNombreUsuario());
    }

    @Test
    public void testCrearNotificacion() {
        NotificacionRequestDTO request = new NotificacionRequestDTO();
        request.setUsuarioId(10);
        request.setMensaje("Hola Mundo");
        request.setTipo(Tipo_Notificacion.ALERTA);

        Notificacion guardada = createNotificacion();
        
        when(notificacionRepository.save(any(Notificacion.class))).thenReturn(guardada);

        when(usuarioService.buscarPorId(10)).thenReturn(UsuarioDTO.builder().nombre("Pedrito Viagney").build());

        NotificacionDTO resultado = notificacionService.crearNotificacion(request);

        assertNotNull(resultado);
        assertEquals("Pedrito Viagney", resultado.getNombreUsuario());
        verify(notificacionRepository, times(1)).save(any(Notificacion.class));
    }

    @Test
    public void testEliminarNotificacion() {
        when(notificacionRepository.existsById(1)).thenReturn(true);
        doNothing().when(notificacionRepository).deleteById(1);

        String mensaje = notificacionService.eliminarNotificacion(1);

        assertEquals("Notificación eliminada correctamente", mensaje);
        verify(notificacionRepository, times(1)).deleteById(1);
    }
}