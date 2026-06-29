package com.example.MicroservicioLibro.controller;

import com.example.MicroservicioLibro.DTO.LibroDTO;
import com.example.MicroservicioLibro.assemblers.LibroModelAssembler;
import com.example.MicroservicioLibro.services.LibroService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v1/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @Autowired
    private LibroModelAssembler assembler;

    @GetMapping(produces = {MediaTypes.HAL_JSON_VALUE, "application/json"})
    public CollectionModel<EntityModel<LibroDTO>> todosLosLibros() {
        List<EntityModel<LibroDTO>> libros = libroService.obtenerTodos()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                libros,
                linkTo(methodOn(LibroController.class).todosLosLibros()).withSelfRel()
        );
    }

    @GetMapping(value = "/{id}", produces = {MediaTypes.HAL_JSON_VALUE, "application/json"})
    public EntityModel<LibroDTO> buscarPorId(@PathVariable Integer id) {
        return assembler.toModel(libroService.buscarPorId(id));
    }

    @PostMapping(produces = {MediaTypes.HAL_JSON_VALUE, "application/json"})
    public ResponseEntity<EntityModel<LibroDTO>> guardarLibro(@RequestBody LibroDTO dto) {
        LibroDTO nuevoLibro = libroService.guardar(dto);
        EntityModel<LibroDTO> model = assembler.toModel(nuevoLibro);

        return ResponseEntity
                .created(model.getRequiredLink("self").toUri())
                .body(model);
    }

    @PutMapping(value = "/{id}", produces = {MediaTypes.HAL_JSON_VALUE, "application/json"})
    public ResponseEntity<EntityModel<LibroDTO>> actualizarLibro(
            @PathVariable Integer id,
            @RequestBody LibroDTO dto) {

        LibroDTO actualizado = libroService.actualizar(id, dto);
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarLibro(@PathVariable Integer id) {
        libroService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}