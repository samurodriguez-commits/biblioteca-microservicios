package com.example.MicroservicioLibro.assemblers;

import com.example.MicroservicioLibro.DTO.AutorDTO;
import com.example.MicroservicioLibro.controller.AutorController;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class AutorModelAssembler implements RepresentationModelAssembler<AutorDTO, EntityModel<AutorDTO>> {

    @Override
    public EntityModel<AutorDTO> toModel(AutorDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(AutorController.class).autorPorId(dto.getId())).withSelfRel(),
                linkTo(methodOn(AutorController.class).todosLosAutores()).withRel("autores"));
    }
}