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
import com.example.MicroservicioLibro.DTO.EditorialDTO;
import com.example.MicroservicioLibro.DTO.LibroDTO;
import com.example.MicroservicioLibro.model.Libro;
import com.example.MicroservicioLibro.repository.LibroRepository;
import com.example.MicroservicioLibro.services.AutorService;
import com.example.MicroservicioLibro.services.EditorialService;
import com.example.MicroservicioLibro.services.LibroService;

@ExtendWith(MockitoExtension.class)
public class LibroServiceTest {

    @Mock
    private LibroRepository libroRepository;

    @Mock
    private AutorService autorService;

    @Mock
    private EditorialService editorialService;

    @InjectMocks
    private LibroService libroService;

    private Libro createLibro() {
        return Libro.builder()
                .Id(1)
                .nombre("Cien años de soledad")
                .fechaPublicacion("1967")
                .autorId(1)
                .editorialId(1)
                .build();
    }

    @Test
    public void testObtenerTodos() {
        Libro libro = createLibro();
        when(libroRepository.findAll()).thenReturn(List.of(libro));
        when(autorService.buscarPorId(1)).thenReturn(AutorDTO.builder().nombre("Onofrio Viagney").build());
        when(editorialService.buscarPorId(1)).thenReturn(EditorialDTO.builder().nombre("Editorial").build());

        List<LibroDTO> libros = libroService.obtenerTodos();

        assertNotNull(libros);
        assertEquals(1, libros.size());
        assertEquals("Onofrio Viagney", libros.get(0).getNombreAutor());
        assertEquals("Editorial", libros.get(0).getNombreEditorial());
    }

    @Test
    public void testBuscarPorId_Exitoso() {
        when(libroRepository.findById(1)).thenReturn(Optional.of(createLibro()));
        when(autorService.buscarPorId(1)).thenReturn(AutorDTO.builder().nombre("Onofrio Viagney").build());
        when(editorialService.buscarPorId(1)).thenReturn(EditorialDTO.builder().nombre("Editorial").build());

        LibroDTO encontrado = libroService.buscarPorId(1);

        assertNotNull(encontrado);
        assertEquals("Cien años de soledad", encontrado.getNombre());
        assertEquals("Onofrio Viagney", encontrado.getNombreAutor());
    }

    @Test
    public void testGuardar() {
        LibroDTO dto = LibroDTO.builder().nombre("Libro").autorId(1).editorialId(1).build();
        Libro libro = createLibro();
        
        when(libroRepository.save(any(Libro.class))).thenReturn(libro);
        
        LibroDTO guardado = libroService.guardar(dto);
        
        assertNotNull(guardado);
        verify(libroRepository, times(1)).save(any(Libro.class));
    }

    @Test
    public void testEliminar() {
        Libro libro = createLibro();
        when(libroRepository.findById(1)).thenReturn(Optional.of(libro));
        
        String resultado = libroService.eliminar(1);
        
        assertTrue(resultado.contains("eliminado exitosamente"));
        verify(libroRepository, times(1)).delete(libro);
    }
}