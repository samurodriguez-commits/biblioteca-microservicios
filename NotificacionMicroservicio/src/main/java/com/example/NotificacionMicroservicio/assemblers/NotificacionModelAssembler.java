package com.example.NotificacionMicroservicio.assemblers;

import com.example.NotificacionMicroservicio.DTO.NotificacionDTO;
import com.example.NotificacionMicroservicio.controller.NotificacionController;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class NotificacionModelAssembler
        implements RepresentationModelAssembler<NotificacionDTO, EntityModel<NotificacionDTO>> {

    @Override
    public EntityModel<NotificacionDTO> toModel(NotificacionDTO dto) {

        return EntityModel.of(dto,
                linkTo(methodOn(NotificacionController.class)
                        .buscarPorId(dto.getId()))
                        .withSelfRel(),

                linkTo(methodOn(NotificacionController.class)
                        .listarTodas())
                        .withRel("notificaciones"));
    }
}