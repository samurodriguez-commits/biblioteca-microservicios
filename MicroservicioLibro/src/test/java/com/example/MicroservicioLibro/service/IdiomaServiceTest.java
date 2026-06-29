package com.example.MicroservicioLibro.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.MicroservicioLibro.DTO.IdiomaDTO;
import com.example.MicroservicioLibro.model.Idioma;
import com.example.MicroservicioLibro.repository.IdiomaRepository;
import com.example.MicroservicioLibro.services.IdiomaService;

@ExtendWith(MockitoExtension.class)
public class IdiomaServiceTest {

    @Mock
    private IdiomaRepository idiomaRepository;

    @InjectMocks
    private IdiomaService idiomaService;

    private Idioma createIdioma() {
        return Idioma.builder()
                .id(1)
                .nombreIdioma("Español")
                .build();
    }

    @Test
    public void testObtenerTodos() {
        when(idiomaRepository.findAll()).thenReturn(List.of(createIdioma()));

        List<IdiomaDTO> lista = idiomaService.obtenerTodos();

        assertNotNull(lista);
        assertEquals(1, lista.size());
        assertEquals("Español", lista.get(0).getNombreIdioma());
    }

    @Test
    public void testBuscarPorId_Exitoso() {
        when(idiomaRepository.findById(1)).thenReturn(Optional.of(createIdioma()));

        IdiomaDTO resultado = idiomaService.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals("Español", resultado.getNombreIdioma());
    }

    @Test
    public void testBuscarPorId_NoEncontrado() {
        when(idiomaRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> idiomaService.buscarPorId(99));
    }

    @Test
    public void testGuardar() {
        Idioma idioma = createIdioma();
        IdiomaDTO dto = IdiomaDTO.builder().nombreIdioma("Español").build();

        when(idiomaRepository.save(any(Idioma.class))).thenReturn(idioma);

        IdiomaDTO guardado = idiomaService.guardar(dto);

        assertNotNull(guardado);
        assertEquals("Español", guardado.getNombreIdioma());
        verify(idiomaRepository, times(1)).save(any(Idioma.class));
    }

    @Test
    public void testEliminar_Exitoso() {
        Idioma idioma = createIdioma();
        when(idiomaRepository.findById(1)).thenReturn(Optional.of(idioma));
        doNothing().when(idiomaRepository).delete(idioma);

        String resultado = idiomaService.eliminar(1);

        assertNotNull(resultado);
        assertTrue(resultado.contains("eliminado exitosamente"));
        verify(idiomaRepository, times(1)).delete(idioma);
    }
}