package springbootapirestjava.repositories.pupil;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.filter.TypeExcludeFilters;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import springbootapirestjava.model.Pupil;
import springbootapirestjava.repositories.PupilRepository;

import javax.transaction.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TypeExcludeFilters(value= DataJpaTypeExcludeFilter.class)
@AutoConfigureCache
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@ImportAutoConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PupilRepositoryJPATest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PupilRepository pupilRepository;

    private final Pupil pupil = Pupil.builder()
            .id("dc7f075e-e4d9-11ec-8fea-0242ac120002")
            .name("Germán")
            .email("germa@german.com")
            .createdAt(Date.from(Instant.now()))
            .build();

    @Test
    @Order(1)
    void getAllTest() {
        entityManager.persist(pupil);
        entityManager.flush();

        assertTrue(pupilRepository.findAll().size() > 0);
    }

    @Test
    @Order(2)
    void getByIdTest() {
        entityManager.persist(pupil);
        entityManager.flush();

        Pupil pupilFound = pupilRepository.findById(pupil.getId()).get();
        assertAll(
                () -> assertNotNull(pupilFound),
                () -> assertEquals(pupil.getId(), pupilFound.getId()),
                () -> assertEquals(pupil.getName(), pupilFound.getName()),
                () -> assertEquals(pupil.getEmail(), pupilFound.getEmail()),
                () -> assertEquals(pupil.getCreatedAt(), pupilFound.getCreatedAt())
        );
    }

    @Test
    @Order(3)
    void save() {
        Pupil pup = pupilRepository.save(pupil);
        assertAll(
                () -> assertNotNull(pup),
                () -> assertEquals(pupil.getId(), pup.getId()),
                () -> assertEquals(pupil.getName(), pup.getName()),
                () -> assertEquals(pupil.getEmail(), pup.getEmail()),
                () -> assertEquals(pupil.getCreatedAt(), pup.getCreatedAt())
        );
    }

    @Test
    @Order(4)
    void update() {
        entityManager.persist(pupil);
        entityManager.flush();
        Pupil pupilFound = pupilRepository.findById(pupil.getId()).get();
        pupilFound.setName("Leonardo");
        Pupil updated = pupilRepository.save(pupilFound);
        assertAll(
                () -> assertNotNull(updated),
                () -> assertEquals(pupil.getId(), updated.getId()),
                () -> assertEquals(pupil.getName(), updated.getName()),
                () -> assertEquals(pupil.getEmail(), updated.getEmail()),
                () -> assertEquals(pupil.getCreatedAt(), updated.getCreatedAt())
        );
    }

    @Test
    @Order(5)
    void delete() {
        entityManager.persist(pupil);
        entityManager.flush();
        pupilRepository.delete(pupil);
        Pupil pup = pupilRepository.findById(pupil.getId()).orElse(null);
        assertNull(pup);
    }
}
