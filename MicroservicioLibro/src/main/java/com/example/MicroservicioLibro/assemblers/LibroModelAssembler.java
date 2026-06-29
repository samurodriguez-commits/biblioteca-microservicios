package com.example.MicroservicioLibro.assemblers;

import com.example.MicroservicioLibro.DTO.LibroDTO;
import com.example.MicroservicioLibro.controller.LibroController;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class LibroModelAssembler implements RepresentationModelAssembler<LibroDTO, EntityModel<LibroDTO>> {
    @Override
    public EntityModel<LibroDTO> toModel(LibroDTO dto) {
        return EntityModel.of(dto,
                // Apuntando al nuevo LibroController 
                linkTo(methodOn(LibroController.class).buscarPorId(dto.getId())).withSelfRel(),
                linkTo(methodOn(LibroController.class).todosLosLibros()).withRel("libros"));
    }
}