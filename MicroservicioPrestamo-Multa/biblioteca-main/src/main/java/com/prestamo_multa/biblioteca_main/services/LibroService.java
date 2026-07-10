package com.prestamo_multa.biblioteca_main.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.prestamo_multa.biblioteca_main.DTO.LibroDTO;

@Service
public class LibroService {

    @Value("${libro.service.url}")
    private String libroServiceUrl;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public LibroDTO buscarPorId(Integer id) {
        return webClientBuilder.build()
            .get()
            .uri(libroServiceUrl + "/" + id)
            .retrieve()
            .bodyToMono(LibroDTO.class)
            .block();
    }

    public List<LibroDTO> obtenerTodos() {
        return webClientBuilder.build()
            .get()
            .uri(libroServiceUrl)
            .retrieve()
            .bodyToFlux(LibroDTO.class)
            .collectList()
            .block();
    }
}
