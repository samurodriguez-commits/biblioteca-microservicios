package com.prestamo_multa.biblioteca_main.assemblers;

import com.prestamo_multa.biblioteca_main.DTO.MultaDTO;
import com.prestamo_multa.biblioteca_main.controller.MultaController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class MultaModelAssembler 
        implements RepresentationModelAssembler<MultaDTO, EntityModel<MultaDTO>> {

    @Override
    public EntityModel<MultaDTO> toModel(MultaDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(MultaController.class).buscarPorId(dto.getId())).withSelfRel(),
                linkTo(methodOn(MultaController.class).listarTodas()).withRel("multas"));
    }
}