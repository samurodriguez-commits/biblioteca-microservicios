package com.example.NotificacionMicroservicio.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.NotificacionMicroservicio.DTO.NotificacionDTO;
import com.example.NotificacionMicroservicio.DTO.NotificacionRequestDTO;
import com.example.NotificacionMicroservicio.assemblers.NotificacionModelAssembler;
import com.example.NotificacionMicroservicio.services.NotificacionService;

import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    @Autowired
    private NotificacionModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<NotificacionDTO>> listarTodas() {

        List<EntityModel<NotificacionDTO>> notificaciones =
                notificacionService.obtenerTodas()
                        .stream()
                        .map(assembler::toModel)
                        .collect(Collectors.toList());

        return CollectionModel.of(
                notificaciones,
                linkTo(methodOn(NotificacionController.class)
                        .listarTodas())
                        .withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<NotificacionDTO> buscarPorId(@PathVariable Integer id) {

        return assembler.toModel(
                notificacionService.buscarPorId(id));
    }

    @GetMapping(value = "/usuario/{usuarioId}",
            produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<NotificacionDTO>> listarPorUsuario(
            @PathVariable Integer usuarioId) {

        List<EntityModel<NotificacionDTO>> notificaciones =
                notificacionService.listarPorUsuario(usuarioId)
                        .stream()
                        .map(assembler::toModel)
                        .collect(Collectors.toList());

        return CollectionModel.of(
                notificaciones,
                linkTo(methodOn(NotificacionController.class)
                        .listarPorUsuario(usuarioId))
                        .withSelfRel());
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<NotificacionDTO>> crear(
            @Valid @RequestBody NotificacionRequestDTO request) {

        NotificacionDTO nueva =
                notificacionService.crearNotificacion(request);

        EntityModel<NotificacionDTO> model =
                assembler.toModel(nueva);

        return ResponseEntity
                .created(model.getRequiredLink("self").toUri())
                .body(model);
    }

    @PutMapping(value = "/{id}",
            produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<NotificacionDTO>> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody NotificacionRequestDTO request) {

        NotificacionDTO actualizada =
                notificacionService.actualizarNotificacion(id, request);

        return ResponseEntity.ok(
                assembler.toModel(actualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {

        notificacionService.eliminarNotificacion(id);

        return ResponseEntity.noContent().build();
    }
}