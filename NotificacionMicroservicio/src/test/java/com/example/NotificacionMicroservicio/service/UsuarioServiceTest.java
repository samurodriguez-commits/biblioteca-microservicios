package com.example.NotificacionMicroservicio.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.NotificacionMicroservicio.DTO.UsuarioDTO;
import com.example.NotificacionMicroservicio.model.Usuario;
import com.example.NotificacionMicroservicio.repository.UsuarioRepository;
import com.example.NotificacionMicroservicio.services.UsuarioService;

@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    private Usuario createUsuario() {
        return Usuario.builder()
                .id(1)
                .nombre("Pedrito Viagney")
                .correo("Pedrito@gmail.com")
                .numero(934256235)
                .build();
    }

    @Test
    public void testObtenerTodos() {
        when(usuarioRepository.findAll()).thenReturn(List.of(createUsuario()));
        
        List<UsuarioDTO> usuarios = usuarioService.obtenerTodos();
        
        assertNotNull(usuarios);
        assertEquals(1, usuarios.size());
        assertEquals("Pedrito Viagney", usuarios.get(0).getNombre());
    }

    @Test
    public void testBuscarPorId() {
        Usuario usuario = createUsuario();
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));
        
        UsuarioDTO encontrado = usuarioService.buscarPorId(1);
        
        assertNotNull(encontrado);
        assertEquals("Pedrito Viagney", encontrado.getNombre());
    }

    @Test
    public void testGuardar() {
        Usuario usuario = createUsuario();
        UsuarioDTO dto = UsuarioDTO.builder()
                .nombre("Pedrito Viagney")
                .correo("Pedrito@gmail.com")
                .numero(934256235)
                .build();

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);
        
        UsuarioDTO guardado = usuarioService.guardar(dto);
        
        assertNotNull(guardado);
        assertEquals("Pedrito Viagney", guardado.getNombre());
    }

    @Test
    public void testEliminar() {
        Usuario usuario = createUsuario();
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));
        doNothing().when(usuarioRepository).delete(usuario);
        
        String resultado = usuarioService.eliminar(1);
        
        assertNotNull(resultado);
        assertTrue(resultado.contains("eliminado exitosamente"));
        verify(usuarioRepository, times(1)).delete(usuario);
    }
}