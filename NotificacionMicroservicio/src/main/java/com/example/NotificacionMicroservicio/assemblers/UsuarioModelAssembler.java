package com.example.NotificacionMicroservicio.assemblers;

import com.example.NotificacionMicroservicio.DTO.UsuarioDTO;
import com.example.NotificacionMicroservicio.controller.UsuarioController;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UsuarioModelAssembler
        implements RepresentationModelAssembler<UsuarioDTO, EntityModel<UsuarioDTO>> {

    @Override
    public EntityModel<UsuarioDTO> toModel(UsuarioDTO dto) {

        return EntityModel.of(dto,
                linkTo(methodOn(UsuarioController.class)
                        .buscarPorId(dto.getId()))
                        .withSelfRel(),

                linkTo(methodOn(UsuarioController.class)
                        .todosLosUsuarios())
                        .withRel("usuarios"));
    }
}