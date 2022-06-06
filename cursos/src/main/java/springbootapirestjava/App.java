package springbootapirestjava;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springbootapirestjava.model.*;
import springbootapirestjava.repositories.*;
import springbootapirestjava.service.StorageService;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    Pupil pupil = Pupil.builder()
            .id(UUID.randomUUID().toString())
            .name("Emilio")
            .email("emilio@emilio.com\"")
            .build();

    Tuition tuition = Tuition.builder()
            .id(UUID.randomUUID().toString())
            .build();

    Modulo modulo = Modulo.builder()
            .id(UUID.randomUUID().toString())
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
            pupilRepository.save(pupil);
            tuitionRepository.save(tuition);
            moduleRepository.save(modulo);
            courseRepository.save(course);
            claseRepository.save(clase);

            //pupilRepository.findAll().forEach(System.out::println);
        });
    }
}
