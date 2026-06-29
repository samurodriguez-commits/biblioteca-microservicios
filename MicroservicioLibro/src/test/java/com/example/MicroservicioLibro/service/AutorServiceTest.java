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

import com.example.MicroservicioLibro.DTO.AutorDTO;
import com.example.MicroservicioLibro.model.Autor;
import com.example.MicroservicioLibro.repository.AutorRepository;
import com.example.MicroservicioLibro.services.AutorService;

@ExtendWith(MockitoExtension.class)
public class AutorServiceTest {

    @Mock
    private AutorRepository autorRepository;

    @InjectMocks
    private AutorService autorService;

    private Autor createAutor() {
        return Autor.builder()
                .id(1)
                .nombre("Onofrio Viagney")
                .rut("12345678-9")
                .correo("onofrio@gmail.com")
                .numero("123456789")
                .ubicacion("Colombia")
                .nacionalidad("Colombiana")
                .build();
    }

    @Test
    public void testObtenerTodos() {
        when(autorRepository.findAll()).thenReturn(List.of(createAutor()));
        List<AutorDTO> autores = autorService.obtenerTodos();
        assertNotNull(autores);
        assertEquals(1, autores.size());
        assertEquals("Onofrio Viagney", autores.get(0).getNombre());
    }

    @Test
    public void testBuscarPorId() {
        Autor autor = createAutor();
        when(autorRepository.findById(1)).thenReturn(Optional.of(autor));

        AutorDTO encontrado = autorService.buscarPorId(1);

        assertNotNull(encontrado);
        assertEquals("Onofrio Viagney", encontrado.getNombre());
    }

    @Test
    public void testGuardar() {
        Autor autor = createAutor();
        AutorDTO dto = AutorDTO.builder()
                .nombre("Onofrio Viagney")
                .rut("12345678-9")
                .correo("onofrio@gmail.com")
                .build();

        when(autorRepository.save(any(Autor.class))).thenReturn(autor);

        AutorDTO guardado = autorService.guardar(dto);

        assertNotNull(guardado);
        assertEquals("Onofrio Viagney", guardado.getNombre());
    }

    @Test
    public void testEliminar() {
        when(autorRepository.existsById(1)).thenReturn(true);
        doNothing().when(autorRepository).deleteById(1);

        String resultado = autorService.eliminar(1);

        assertNotNull(resultado);
        assertTrue(resultado.contains("eliminado correctamente"));
        verify(autorRepository, times(1)).deleteById(1);
    }

    @Test
    public void testActualizar() {
        Autor autorExistente = createAutor();
        AutorDTO dtoActualizado = AutorDTO.builder().nombre("Gabo").build();
        
        when(autorRepository.findById(1)).thenReturn(Optional.of(autorExistente));
        when(autorRepository.save(any(Autor.class))).thenReturn(autorExistente);

        AutorDTO resultado = autorService.actualizar(1, dtoActualizado);

        assertNotNull(resultado);
        verify(autorRepository, times(1)).save(any(Autor.class));
    }
}