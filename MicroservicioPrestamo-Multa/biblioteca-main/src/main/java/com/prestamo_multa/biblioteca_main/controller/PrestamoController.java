package com.prestamo_multa.biblioteca_main.controller;

import com.prestamo_multa.biblioteca_main.DTO.PrestamoDTO;
import com.prestamo_multa.biblioteca_main.DTO.PrestamoRequestDTO;
import com.prestamo_multa.biblioteca_main.assemblers.PrestamoModelAssembler;
import com.prestamo_multa.biblioteca_main.model.EstadoPrestamo;
import com.prestamo_multa.biblioteca_main.services.PrestamoService;
import jakarta.validation.Valid;
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
@RequestMapping("/api/prestamos")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    @Autowired
    private PrestamoModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<PrestamoDTO>> listarTodos() {
        List<EntityModel<PrestamoDTO>> prestamos = prestamoService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(prestamos,
                linkTo(methodOn(PrestamoController.class).listarTodos()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<PrestamoDTO> buscarPorId(@PathVariable Integer id) {
        return assembler.toModel(prestamoService.buscarPorId(id));
    }

    @GetMapping(value = "/usuario/{usuarioId}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<PrestamoDTO>> listarPorUsuario(@PathVariable Integer usuarioId) {
        List<EntityModel<PrestamoDTO>> prestamos = prestamoService.listarPorUsuario(usuarioId).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(prestamos,
                linkTo(methodOn(PrestamoController.class).listarPorUsuario(usuarioId)).withSelfRel());
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<PrestamoDTO>> crear(@Valid @RequestBody PrestamoRequestDTO request) {
        PrestamoDTO nuevo = prestamoService.crearPrestamo(request);
        EntityModel<PrestamoDTO> model = assembler.toModel(nuevo);
        return ResponseEntity.created(model.getRequiredLink("self").toUri()).body(model);
    }

    @PutMapping(value = "/{id}/estado", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<PrestamoDTO>> cambiarEstado(
            @PathVariable Integer id, 
            @RequestParam EstadoPrestamo estado) {
        PrestamoDTO actualizado = prestamoService.actualizarEstado(id, estado);
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }
}