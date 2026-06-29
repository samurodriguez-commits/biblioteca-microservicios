package com.example.MicroservicioLibro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.MicroservicioLibro.DTO.InventarioDTO;
import com.example.MicroservicioLibro.services.InventarioService;

@RestController
@RequestMapping("/api/v1/inventarios")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @GetMapping
    public ResponseEntity<List<InventarioDTO>> todosLosInventarios() {
        List<InventarioDTO> inventarios = inventarioService.obtenerTodos();
        return inventarios.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(inventarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(inventarioService.buscarPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<InventarioDTO> guardarInventario(@RequestBody InventarioDTO inventarioDTO) {
        return new ResponseEntity<>(inventarioService.guardar(inventarioDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarInventario(@PathVariable Integer id, @RequestBody InventarioDTO inventarioDTO) {
        try {
            return ResponseEntity.ok(inventarioService.actualizar(id, inventarioDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarInventario(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(inventarioService.eliminar(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}