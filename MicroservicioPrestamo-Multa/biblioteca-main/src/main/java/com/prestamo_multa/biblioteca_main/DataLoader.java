package com.prestamo_multa.biblioteca_main;

import com.prestamo_multa.biblioteca_main.model.Prestamo;
import com.prestamo_multa.biblioteca_main.model.EstadoMulta;
import com.prestamo_multa.biblioteca_main.model.EstadoPrestamo;
import com.prestamo_multa.biblioteca_main.model.Multa;
import com.prestamo_multa.biblioteca_main.repository.PrestamoRepository;
import com.prestamo_multa.biblioteca_main.repository.MultaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import net.datafaker.Faker;

import java.time.LocalDate;
import java.util.Random;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private MultaRepository multaRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();

        // Generar 15 Préstamos de prueba
        for (int i = 0; i < 15; i++) {
            Prestamo prestamo = Prestamo.builder()
                .usuarioId(faker.number().numberBetween(1, 20))
                .libroId(faker.number().numberBetween(1, 100))
                .fechaPrestamo(LocalDate.now().minusDays(faker.number().numberBetween(5, 15)))
                .fechaDevolucion(LocalDate.now().plusDays(faker.number().numberBetween(1, 7)))
                .estado(EstadoPrestamo.values()[random.nextInt(EstadoPrestamo.values().length)])
                .build();
            prestamoRepository.save(prestamo);
        }

        // Generar 5 Multas de prueba
        for (int i = 0; i < 5; i++) {
            Multa multa = Multa.builder()
                .usuarioId(faker.number().numberBetween(1, 20))
                .prestamoId(faker.number().numberBetween(1, 15))
                .monto((double) faker.number().numberBetween(1000, 5000))
                .fechaGenerada(LocalDate.now().minusDays(faker.number().numberBetween(1, 5)))
                .estado(EstadoMulta.values()[random.nextInt(EstadoMulta.values().length)])
                .build();
            multaRepository.save(multa);
        }
    }
}