package com.prestamo_multa.biblioteca_main.services;

import com.prestamo_multa.biblioteca_main.DTO.MultaDTO;
import com.prestamo_multa.biblioteca_main.DTO.MultaRequestDTO;
import com.prestamo_multa.biblioteca_main.model.Estado_Multa;
import com.prestamo_multa.biblioteca_main.model.Multa;
import com.prestamo_multa.biblioteca_main.repository.MultaRepository;

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
public class MultaServiceTest {

    @Mock
    private MultaRepository multaRepository;

    @InjectMocks
    private MultaService multaService;

    private Multa multaSimulada;
    private MultaRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        multaSimulada = Multa.builder()
                .id(1)
                .usuarioId(10)
                .prestamoId(5)
                .monto(3000)
                .fechaGenerada(LocalDate.now())
                .estado(Estado_Multa.PENDIENTE)
                .build();

        requestDTO = new MultaRequestDTO();
        requestDTO.setUsuarioId(10);
        requestDTO.setPrestamoId(5);
        requestDTO.setMonto(3000);
    }

    @Test
    void cuandoBuscarPorId_entoncesRetornaMultaDTO() {
        // Arrange
        when(multaRepository.findById(1)).thenReturn(Optional.of(multaSimulada));

        // Act
        MultaDTO resultado = multaService.buscarPorId(1);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals(Estado_Multa.PENDIENTE, resultado.getEstado());
        verify(multaRepository, times(1)).findById(1);
    }

    @Test
    void cuandoPagarMulta_entoncesCambiaEstadoAPagada() {
        // Arrange
        when(multaRepository.findById(1)).thenReturn(Optional.of(multaSimulada));
        when(multaRepository.save(any(Multa.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        MultaDTO resultado = multaService.pagarMulta(1);

        // Assert
        assertNotNull(resultado);
        assertEquals(Estado_Multa.PAGADA, resultado.getEstado());
        verify(multaRepository, times(1)).save(any(Multa.class));
    }
}