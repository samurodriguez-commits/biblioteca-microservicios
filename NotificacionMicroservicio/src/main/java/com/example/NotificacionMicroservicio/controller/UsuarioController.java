package com.example.NotificacionMicroservicio.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.NotificacionMicroservicio.DTO.UsuarioDTO;
import com.example.NotificacionMicroservicio.assemblers.UsuarioModelAssembler;
import com.example.NotificacionMicroservicio.services.UsuarioService;

import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<UsuarioDTO>> todosLosUsuarios() {

        List<EntityModel<UsuarioDTO>> usuarios =
                usuarioService.obtenerTodos()
                        .stream()
                        .map(assembler::toModel)
                        .collect(Collectors.toList());
        return CollectionModel.of(
                usuarios,
                linkTo(methodOn(UsuarioController.class)
                        .todosLosUsuarios())
                        .withSelfRel());
    }

    @GetMapping(value = "/{id}",
            produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<UsuarioDTO> buscarPorId(@PathVariable Integer id) {
        return assembler.toModel(
                usuarioService.buscarPorId(id));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<UsuarioDTO>> guardarUsuario(
            @Valid @RequestBody UsuarioDTO dto) {
        UsuarioDTO usuario = usuarioService.guardar(dto);
        EntityModel<UsuarioDTO> model = assembler.toModel(usuario);
        return ResponseEntity
                .created(model.getRequiredLink("self").toUri())
                .body(model);
    }

    @PutMapping(value = "/{id}",
            produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<UsuarioDTO>> actualizarUsuario(
            @PathVariable Integer id,
            @Valid @RequestBody UsuarioDTO dto) {
        UsuarioDTO actualizado =
                usuarioService.actualizar(id, dto);
        return ResponseEntity.ok(
                assembler.toModel(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(
            @PathVariable Integer id) {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}