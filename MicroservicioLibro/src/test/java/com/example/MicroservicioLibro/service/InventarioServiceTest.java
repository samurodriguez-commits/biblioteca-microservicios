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

import com.example.MicroservicioLibro.DTO.InventarioDTO;
import com.example.MicroservicioLibro.model.Inventario;
import com.example.MicroservicioLibro.repository.InventarioRepository;
import com.example.MicroservicioLibro.services.InventarioService;

@ExtendWith(MockitoExtension.class)
public class InventarioServiceTest {

    @Mock
    private InventarioRepository inventarioRepository;

    @InjectMocks
    private InventarioService inventarioService;

    private Inventario createInventario() {
        return Inventario.builder()
                .id(1)
                .servicioControlProductos("Activo")
                .servicioComprasProveedores("Activo")
                .servicioVentasPedidos("Activo")
                .servicioStockAlmacen("Activo")
                .build();
    }

    @Test
    public void testObtenerTodos() {
        when(inventarioRepository.findAll()).thenReturn(List.of(createInventario()));

        List<InventarioDTO> lista = inventarioService.obtenerTodos();

        assertNotNull(lista);
        assertEquals(1, lista.size());
        assertEquals("Activo", lista.get(0).getServicioControlProductos());
    }

    @Test
    public void testBuscarPorId_Exitoso() {
        when(inventarioRepository.findById(1)).thenReturn(Optional.of(createInventario()));

        InventarioDTO resultado = inventarioService.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
    }

    @Test
    public void testGuardar() {
        Inventario inventario = createInventario();
        InventarioDTO dto = InventarioDTO.builder()
                .servicioControlProductos("Activo")
                .servicioComprasProveedores("Activo")
                .servicioVentasPedidos("Activo")
                .servicioStockAlmacen("Activo")
                .build();

        when(inventarioRepository.save(any(Inventario.class))).thenReturn(inventario);

        InventarioDTO guardado = inventarioService.guardar(dto);

        assertNotNull(guardado);
        assertEquals("Activo", guardado.getServicioControlProductos());
        verify(inventarioRepository, times(1)).save(any(Inventario.class));
    }

    @Test
    public void testActualizar() {
        Inventario inventarioExistente = createInventario();
        InventarioDTO dtoActualizado = InventarioDTO.builder()
                .servicioControlProductos("Inactivo")
                .build();

        when(inventarioRepository.findById(1)).thenReturn(Optional.of(inventarioExistente));
        when(inventarioRepository.save(any(Inventario.class))).thenReturn(inventarioExistente);

        InventarioDTO resultado = inventarioService.actualizar(1, dtoActualizado);

        assertNotNull(resultado);
        verify(inventarioRepository, times(1)).save(any(Inventario.class));
    }

    @Test
    public void testEliminar_Exitoso() {
        when(inventarioRepository.existsById(1)).thenReturn(true);
        doNothing().when(inventarioRepository).deleteById(1);

        String mensaje = inventarioService.eliminar(1);

        assertTrue(mensaje.contains("eliminado exitosamente"));
        verify(inventarioRepository, times(1)).deleteById(1);
    }

    @Test
    public void testEliminar_NoEncontrado() {
        when(inventarioRepository.existsById(99)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> inventarioService.eliminar(99));
    }
}