package com.prestamo_multa.biblioteca_main.controller;

import com.prestamo_multa.biblioteca_main.DTO.MultaDTO;
import com.prestamo_multa.biblioteca_main.DTO.MultaRequestDTO;
import com.prestamo_multa.biblioteca_main.assemblers.MultaModelAssembler;
import com.prestamo_multa.biblioteca_main.services.MultaService;
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
@RequestMapping("/api/multas")
public class MultaController {

    @Autowired
    private MultaService multaService;

    @Autowired
    private MultaModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<MultaDTO>> listarTodas() {
        List<EntityModel<MultaDTO>> multas = multaService.obtenerTodas().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(multas,
                linkTo(methodOn(MultaController.class).listarTodas()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<MultaDTO> buscarPorId(@PathVariable Integer id) {
        return assembler.toModel(multaService.buscarPorId(id));
    }

    @GetMapping(value = "/usuario/{usuarioId}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<MultaDTO>> listarPorUsuario(@PathVariable Integer usuarioId) {
        List<EntityModel<MultaDTO>> multas = multaService.listarPorUsuario(usuarioId).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(multas,
                linkTo(methodOn(MultaController.class).listarPorUsuario(usuarioId)).withSelfRel());
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<MultaDTO>> crear(@Valid @RequestBody MultaRequestDTO request) {
        MultaDTO nueva = multaService.crearMulta(request);
        EntityModel<MultaDTO> model = assembler.toModel(nueva);
        return ResponseEntity.created(model.getRequiredLink("self").toUri()).body(model);
    }

    @PutMapping(value = "/{id}/pagar", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<MultaDTO>> pagar(@PathVariable Integer id) {
        MultaDTO pagada = multaService.pagarMulta(id);
        return ResponseEntity.ok(assembler.toModel(pagada));
    }
}