package com.example.MicroservicioLibro.controller;

import com.example.MicroservicioLibro.DTO.AutorDTO;
import com.example.MicroservicioLibro.assemblers.AutorModelAssembler;
import com.example.MicroservicioLibro.services.AutorService;

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
@RequestMapping("/api/v1/autores")
public class AutorController {

    @Autowired
    private AutorService autorService;
    
    @Autowired
    private AutorModelAssembler assembler;

    @GetMapping(produces = {MediaTypes.HAL_JSON_VALUE, "application/json"})
    public CollectionModel<EntityModel<AutorDTO>> todosLosAutores() {
        List<EntityModel<AutorDTO>> autores = autorService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(autores,
                linkTo(methodOn(AutorController.class).todosLosAutores()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = {MediaTypes.HAL_JSON_VALUE, "application/json"})
    public EntityModel<AutorDTO> autorPorId(@PathVariable Integer id) {
        return assembler.toModel(autorService.buscarPorId(id));
    }

    @PostMapping(produces = {MediaTypes.HAL_JSON_VALUE, "application/json"})
    public ResponseEntity<EntityModel<AutorDTO>> crearAutor(@RequestBody AutorDTO dto) {
        AutorDTO nuevoAutor = autorService.guardar(dto);
        EntityModel<AutorDTO> model = assembler.toModel(nuevoAutor);
        return ResponseEntity.created(model.getRequiredLink("self").toUri()).body(model);
    }

    @PutMapping(value = "/{id}", produces = {MediaTypes.HAL_JSON_VALUE, "application/json"})
    public ResponseEntity<EntityModel<AutorDTO>> actualizarAutor(@PathVariable Integer id, @RequestBody AutorDTO dto) {
        AutorDTO actualizado = autorService.actualizar(id, dto);
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarAutor(@PathVariable Integer id) {
        autorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}