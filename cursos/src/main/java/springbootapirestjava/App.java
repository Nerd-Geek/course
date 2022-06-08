package springbootapirestjava;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import springbootapirestjava.controller.PupilController;
import springbootapirestjava.dto.pupil.CreatePupilDTO;
import springbootapirestjava.model.*;
import springbootapirestjava.repositories.*;
import springbootapirestjava.service.pupil.PupilEntityService;
import springbootapirestjava.service.update.StorageService;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    Pupil pupilNormal = Pupil.builder()
            .id("c55813de-cdba-42c6-9554-579e4368d941")
            .name("Emilio")
            .password("$2a$10$ZjppaBXsfDf/ZXO0UgrblufVTS5l7HrXAw/MntszWMxrJblNmcG0m")
            .email("emilio@emilio.com")
            .rols(Collections.singleton(PupilRol.PUPIL))
            .build();

    Pupil pupilAdmin = Pupil.builder()
            .id("c55813de-cdba-42c6-9554-579e4368d942")
            .name("Andres")
            .password("$2a$10$ZjppaBXsfDf/ZXO0UgrblufVTS5l7HrXAw/MntszWMxrJblNmcG0m")
            .email("andres@andres.com")
            .rols(Collections.singleton(PupilRol.ADMIN))
            .build();

    Tuition tuition = Tuition.builder()
            .id("47800823-edb7-4cc5-85ce-b578b2f6c47d ")
            .build();

    Modulo modulo = Modulo.builder()
            .id("4203de85-4295-4ff4-b776-033faec08005")
            .name("Acceso a datos")
            .acronym("AD")
            .build();

    Course course = Course.builder()
            .id("a4594ad4-e4ba-11ec-8fea-0242ac120002")
            .namberCourse(NamberCourse.First)
            .acronym("DAM")
            .createdAt(Date.from(Instant.now()))
            .build();

    Clase clase = Clase.builder()
            .id("05e9c2e3-6639-4581-a2f5-cfedd5db8437")
            .clasification(5.0)
            .build();

    @Bean
    CommandLineRunner start(PupilRepository pupilRepository, TuitionRepository tuitionRepository, ModuleRepository moduleRepository,
                            CourseRepository courseRepository, ClaseRepository claseRepository, StorageService storageService) {
        return (args -> {
            storageService.deleteAll();
            storageService.init();
            pupilRepository.save(pupilNormal);
            pupilRepository.save(pupilAdmin);
            tuitionRepository.save(tuition);
            moduleRepository.save(modulo);
            courseRepository.save(course);
            claseRepository.save(clase);
        });
    }
}
