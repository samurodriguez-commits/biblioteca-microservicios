package com.example.MicroservicioLibro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.MicroservicioLibro.DTO.MaterialDTO;
import com.example.MicroservicioLibro.services.MaterialService;

@RestController
@RequestMapping("/api/v1/materiales")
public class MaterialController {
    
    @Autowired
    private MaterialService materialService;

    @GetMapping
    public ResponseEntity<List<MaterialDTO>> todosLosMateriales() {
        List<MaterialDTO> materiales = materialService.obtenerTodos();
        return materiales.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(materiales);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(materialService.buscarPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<MaterialDTO> guardarMaterial(@RequestBody MaterialDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(materialService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarMaterial(@PathVariable Integer id, @RequestBody MaterialDTO dto) {
        try {
            return ResponseEntity.ok(materialService.actualizar(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarMaterial(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(materialService.eliminar(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
