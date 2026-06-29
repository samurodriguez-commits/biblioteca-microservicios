package com.prestamo_multa.biblioteca_main.assemblers;

import com.prestamo_multa.biblioteca_main.DTO.PrestamoDTO;
import com.prestamo_multa.biblioteca_main.controller.PrestamoController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class PrestamoModelAssembler 
        implements RepresentationModelAssembler<PrestamoDTO, EntityModel<PrestamoDTO>> {

    @Override
    public EntityModel<PrestamoDTO> toModel(PrestamoDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(PrestamoController.class).buscarPorId(dto.getId())).withSelfRel(),
                linkTo(methodOn(PrestamoController.class).listarTodos()).withRel("prestamos"));
    }
}