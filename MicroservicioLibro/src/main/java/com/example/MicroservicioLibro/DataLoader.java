package com.example.MicroservicioLibro;

import com.example.MicroservicioLibro.model.*;
import com.example.MicroservicioLibro.repository.*;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired 
    private AutorRepository autorRepository;

    @Autowired 
    private EditorialRepository editorialRepository;

    @Autowired 
    private IdiomaRepository idiomaRepository;

    @Autowired 
    private LibroRepository libroRepository;

    @Autowired 
    private MaterialRepository materialRepository;

    @Autowired 
    private InventarioRepository inventarioRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            autorRepository.save(Autor.builder()
                .nombre(faker.book().author())
                .rut(faker.idNumber().valid())
                .correo(faker.internet().emailAddress())
                .nacionalidad(faker.country().name())
                .build());
        }

        for (int i = 0; i < 3; i++) {
            editorialRepository.save(Editorial.builder()
                .nombre(faker.book().publisher())
                .correoContacto(faker.internet().emailAddress())
                .build());
        }

        String[] lenguajes = {"Español", "Inglés", "Francés", "Alemán"};
        for (String lang : lenguajes) {
            idiomaRepository.save(Idioma.builder().nombreIdioma(lang).build());
        }

        List<Autor> autores = autorRepository.findAll();
        List<Editorial> editoriales = editorialRepository.findAll();
        List<Idioma> idiomas = idiomaRepository.findAll();

        for (int i = 0; i < 10; i++) {
            libroRepository.save(Libro.builder()
                .nombre(faker.book().title())
                .fechaPublicacion(String.valueOf(faker.number().numberBetween(1990, 2024)))
                .autorId(autores.get(random.nextInt(autores.size())).getId())
                .editorialId(editoriales.get(random.nextInt(editoriales.size())).getId())
                .idiomaId(idiomas.get(random.nextInt(idiomas.size())).getId())
                .build());
        }

        List<Libro> libros = libroRepository.findAll();
        for (int i = 0; i < 15; i++) {
            materialRepository.save(Material.builder()
                .nombreMaterial("Copia de " + faker.book().title())
                .tipoMaterial(faker.options().option("Físico", "Digital"))
                .estado("Disponible")
                .libroId(libros.get(random.nextInt(libros.size())).getId())
                .build());
        }

        inventarioRepository.save(Inventario.builder()
                .servicioControlProductos("Activo")
                .servicioComprasProveedores("Activo")
                .servicioVentasPedidos("Activo")
                .servicioStockAlmacen("Activo")
                .build());
        
        System.out.println("DataLoader: Datos cargados exitosamente (Autores, Editoriales, Idiomas, Libros, Materiales e Inventario).");
    }
}