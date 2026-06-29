package com.example.MicroservicioLibro.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.MicroservicioLibro.DTO.InventarioDTO;
import com.example.MicroservicioLibro.model.Inventario;
import com.example.MicroservicioLibro.repository.InventarioRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class InventarioService {
    
    @Autowired
    private InventarioRepository inventarioRepository;

    public List<InventarioDTO> obtenerTodos() {
        log.info("Service: Listando todos los registros de inventario");
        return inventarioRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public InventarioDTO buscarPorId(Integer id) {
        log.info("Service: Buscando registro de inventario con ID: {}", id);
        Inventario inventario = inventarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Registro de inventario no encontrado con ID: " + id));
        return convertirADTO(inventario);
    }

    public InventarioDTO guardar(InventarioDTO dto) {
        log.info("Service: Registrando nuevo movimiento en inventario");
        
        Inventario inventario = Inventario.builder()
                .servicioControlProductos(dto.getServicioControlProductos())
                .servicioComprasProveedores(dto.getServicioComprasProveedores())
                .servicioVentasPedidos(dto.getServicioVentasPedidos())
                .servicioStockAlmacen(dto.getServicioStockAlmacen())
                .build();

        return convertirADTO(inventarioRepository.save(inventario));
    }

    public InventarioDTO actualizar(Integer id, InventarioDTO dto) {
        log.info("Service: Actualizando registro de inventario ID: {}", id);
        
        Inventario inventario = inventarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No es posible actualizar, el registro con ID " + id + " no existe."));
        
        // Actualización condicional de campos
        if (dto.getServicioControlProductos() != null) 
            inventario.setServicioControlProductos(dto.getServicioControlProductos());
        if (dto.getServicioComprasProveedores() != null) 
            inventario.setServicioComprasProveedores(dto.getServicioComprasProveedores());
        if (dto.getServicioVentasPedidos() != null) 
            inventario.setServicioVentasPedidos(dto.getServicioVentasPedidos());
        if (dto.getServicioStockAlmacen() != null) 
            inventario.setServicioStockAlmacen(dto.getServicioStockAlmacen());
            
        return convertirADTO(inventarioRepository.save(inventario));
    }

    public String eliminar(Integer id) {
        log.warn("Service: Intentando eliminar registro de inventario ID: {}", id);
        
        if (!inventarioRepository.existsById(id)) {
            throw new RuntimeException("¡Imposible eliminar! El registro con ID " + id + " no existe.");
        }
        
        inventarioRepository.deleteById(id);
        return "El registro de inventario con ID " + id + " ha sido eliminado exitosamente.";
    }

    private InventarioDTO convertirADTO(Inventario i) {
        return InventarioDTO.builder()
                .id(i.getId())
                .servicioControlProductos(i.getServicioControlProductos())
                .servicioComprasProveedores(i.getServicioComprasProveedores())
                .servicioVentasPedidos(i.getServicioVentasPedidos())
                .servicioStockAlmacen(i.getServicioStockAlmacen())
                .build();
    }
}