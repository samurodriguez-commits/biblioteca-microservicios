package com.prestamo_multa.biblioteca_main.services;

import com.prestamo_multa.biblioteca_main.DTO.PrestamoDTO;
import com.prestamo_multa.biblioteca_main.DTO.PrestamoRequestDTO;
import com.prestamo_multa.biblioteca_main.model.Estado_Prestamo;
import com.prestamo_multa.biblioteca_main.model.Prestamo;
import com.prestamo_multa.biblioteca_main.repository.PrestamoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PrestamoServiceTest {

    @Mock
    private PrestamoRepository prestamoRepository;

    @InjectMocks
    private PrestamoService prestamoService;

    private Prestamo prestamoSimulado;
    private PrestamoRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        prestamoSimulado = Prestamo.builder()
                .id(1)
                .usuarioId(10)
                .libroId(50)
                .fechaPrestamo(LocalDate.now())
                .fechaDevolucion(LocalDate.now().plusDays(7))
                .estado(Estado_Prestamo.ACTIVO)
                .build();

        requestDTO = new PrestamoRequestDTO();
        requestDTO.setUsuarioId(10);
        requestDTO.setLibroId(50);
    }

    @Test
    void cuandoBuscarPorId_entoncesRetornaPrestamoDTO() {
        // Arrange
        when(prestamoRepository.findById(1)).thenReturn(Optional.of(prestamoSimulado));

        // Act
        PrestamoDTO resultado = prestamoService.buscarPorId(1);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals(Estado_Prestamo.ACTIVO, resultado.getEstado());
        verify(prestamoRepository, times(1)).findById(1);
    }

    @Test
    void cuandoCrearPrestamo_entoncesGuardaYRetornaDTO() {
        // Arrange
        when(prestamoRepository.save(any(Prestamo.class))).thenReturn(prestamoSimulado);

        // Act
        PrestamoDTO resultado = prestamoService.crearPrestamo(requestDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals(Estado_Prestamo.ACTIVO, resultado.getEstado());
        verify(prestamoRepository, times(1)).save(any(Prestamo.class));
    }
}