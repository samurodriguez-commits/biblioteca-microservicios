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

import com.example.MicroservicioLibro.DTO.LibroDTO;
import com.example.MicroservicioLibro.DTO.MaterialDTO;
import com.example.MicroservicioLibro.model.Material;
import com.example.MicroservicioLibro.repository.MaterialRepository;
import com.example.MicroservicioLibro.services.LibroService;
import com.example.MicroservicioLibro.services.MaterialService;

@ExtendWith(MockitoExtension.class)
public class MaterialServiceTest {

    @Mock
    private MaterialRepository materialRepository;

    @Mock
    private LibroService libroService;

    @InjectMocks
    private MaterialService materialService;

    private Material createMaterial() {
        return Material.builder()
                .id(1)
                .nombreMaterial("Copia de Cien años de soledad")
                .tipoMaterial("Físico")
                .estado("Disponible")
                .libroId(1)
                .build();
    }

    @Test
    public void testObtenerTodos() {
        Material material = createMaterial();
        when(materialRepository.findAll()).thenReturn(List.of(material));
        when(libroService.buscarPorId(1)).thenReturn(LibroDTO.builder().nombre("Cien años de soledad").build());
        List<MaterialDTO> lista = materialService.obtenerTodos();
        assertNotNull(lista);
        assertEquals(1, lista.size());
        assertEquals("Cien años de soledad", lista.get(0).getNombreLibro());
    }

    @Test
    public void testBuscarPorId_Exitoso() {
        when(materialRepository.findById(1)).thenReturn(Optional.of(createMaterial()));
        when(libroService.buscarPorId(1)).thenReturn(LibroDTO.builder().nombre("Cien años de soledad").build());

        MaterialDTO encontrado = materialService.buscarPorId(1);

        assertNotNull(encontrado);
        assertEquals("Copia de Cien años de soledad", encontrado.getNombreMaterial());
        assertEquals("Físico", encontrado.getTipoMaterial());
        assertEquals("Disponible", encontrado.getEstado());
        assertEquals("Cien años de soledad", encontrado.getNombreLibro());
    }
}