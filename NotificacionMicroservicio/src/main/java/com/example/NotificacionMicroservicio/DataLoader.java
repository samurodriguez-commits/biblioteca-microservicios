package com.example.NotificacionMicroservicio;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.example.NotificacionMicroservicio.model.Notificacion;
import com.example.NotificacionMicroservicio.model.Tipo_Notificacion;
import com.example.NotificacionMicroservicio.model.Usuario;
import com.example.NotificacionMicroservicio.repository.NotificacionRepository;
import com.example.NotificacionMicroservicio.repository.UsuarioRepository;

import net.datafaker.Faker;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();

        for (int i = 0; i < 20; i++) {
            Usuario usuario = Usuario.builder()
                .nombre(faker.name().fullName())
                .correo(faker.internet().emailAddress())
                .numero(faker.number().numberBetween(900000000, 999999999))
                .build();
            usuarioRepository.save(usuario);
        }

        List<Usuario> usuarios = usuarioRepository.findAll();
        for (int i = 0; i < 50; i++) {
            Usuario usuarioAleatorio = usuarios.get(random.nextInt(usuarios.size()));
            
            Notificacion notif = Notificacion.builder()
                .usuarioId(usuarioAleatorio.getId())
                .mensaje(faker.lorem().sentence(10))
                .fechaEnvio(LocalDate.now().minusDays(random.nextInt(30))) // Fecha aleatoria del último mes
                .leida(faker.bool().bool())
                .tipo(Tipo_Notificacion.values()[random.nextInt(Tipo_Notificacion.values().length)])
                .build();
            
            notificacionRepository.save(notif);
        }
    }
}