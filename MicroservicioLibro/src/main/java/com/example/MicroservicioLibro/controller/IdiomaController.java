package com.example.MicroservicioLibro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.MicroservicioLibro.DTO.IdiomaDTO;
import com.example.MicroservicioLibro.services.IdiomaService;

@RestController
@RequestMapping("/api/v1/idiomas")
public class IdiomaController {

    @Autowired
    private IdiomaService idiomaService;

    @GetMapping
    public ResponseEntity<List<IdiomaDTO>> todosLosIdiomas() {
        List<IdiomaDTO> idiomas = idiomaService.obtenerTodos();
        return idiomas.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(idiomas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(idiomaService.buscarPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<IdiomaDTO> guardarIdioma(@RequestBody IdiomaDTO idiomaDTO) {
        return new ResponseEntity<>(idiomaService.guardar(idiomaDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarIdioma(@PathVariable Integer id, @RequestBody IdiomaDTO idiomaDTO) {
        try {
            return ResponseEntity.ok(idiomaService.actualizar(id, idiomaDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarIdioma(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(idiomaService.eliminar(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}