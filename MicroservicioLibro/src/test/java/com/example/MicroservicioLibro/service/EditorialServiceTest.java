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

import com.example.MicroservicioLibro.DTO.EditorialDTO;
import com.example.MicroservicioLibro.model.Editorial;
import com.example.MicroservicioLibro.repository.EditorialRepository;
import com.example.MicroservicioLibro.services.EditorialService;

@ExtendWith(MockitoExtension.class)
public class EditorialServiceTest {

    @Mock
    private EditorialRepository editorialRepository;

    @InjectMocks
    private EditorialService editorialService;

    private Editorial createEditorial() {
        return Editorial.builder()
                .id(1)
                .nombre("Editorial")
                .direccion("Calle 435")
                .telefono("985749574")
                .correoContacto("contacto@gmail.com")
                .build();
    }

    @Test
    public void testObtenerTodas() {
        when(editorialRepository.findAll()).thenReturn(List.of(createEditorial()));

        List<EditorialDTO> editoriales = editorialService.obtenerTodas();

        assertNotNull(editoriales);
        assertEquals(1, editoriales.size());
        assertEquals("Editorial", editoriales.get(0).getNombre());
    }

    @Test
    public void testBuscarPorId() {
        Editorial editorial = createEditorial();
        when(editorialRepository.findById(1)).thenReturn(Optional.of(editorial));

        EditorialDTO encontrado = editorialService.buscarPorId(1);

        assertNotNull(encontrado);
        assertEquals("Editorial", encontrado.getNombre());
    }

    @Test
    public void testGuardar() {
        Editorial editorial = createEditorial();
        EditorialDTO dto = EditorialDTO.builder()
                .nombre("Editorial")
                .direccion("Calle 435")
                .telefono("985749574")
                .correoContacto("contacto@gmail.com")
                .build();

        when(editorialRepository.save(any(Editorial.class))).thenReturn(editorial);

        EditorialDTO guardado = editorialService.guardar(dto);

        assertNotNull(guardado);
        assertEquals("Editorial", guardado.getNombre());
    }

    @Test
    public void testEliminar() {
        when(editorialRepository.existsById(1)).thenReturn(true);
        doNothing().when(editorialRepository).deleteById(1);

        String resultado = editorialService.eliminar(1);

        assertNotNull(resultado);
        assertTrue(resultado.contains("eliminada correctamente"));
        verify(editorialRepository, times(1)).deleteById(1);
    }

    @Test
    public void testActualizar() {
        Editorial editorialExistente = createEditorial();
        EditorialDTO dtoActualizado = EditorialDTO.builder()
                .nombre("Editorial Nueva")
                .build();
        
        when(editorialRepository.findById(1)).thenReturn(Optional.of(editorialExistente));
        when(editorialRepository.save(any(Editorial.class))).thenReturn(editorialExistente);

        EditorialDTO resultado = editorialService.actualizar(1, dtoActualizado);

        assertNotNull(resultado);
        verify(editorialRepository, times(1)).save(any(Editorial.class));
    }
}