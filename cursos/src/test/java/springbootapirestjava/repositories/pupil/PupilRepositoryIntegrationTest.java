package springbootapirestjava.repositories.pupil;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import springbootapirestjava.model.Pupil;
import springbootapirestjava.repositories.PupilRepository;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class PupilRepositoryIntegrationTest {
    private final Pupil pupil = Pupil.builder()
            .id("dc7f075e-e4d9-11ec-8fea-0242ac120002")
            .name("Germán")
            .email("germa@german.com")
            .createdAt(LocalDate.now())
            .build();

    @Autowired
    private PupilRepository pupilRepository;

    @Test
    @Order(1)
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
    @Order(2)
    void getAllPupil() {
        assertTrue(pupilRepository.findAll().size() > 0);
    }

    @Test
    @Order(3)
    void getPupilId() {
        Pupil pupi = pupilRepository.save(pupil);
        Pupil pup = pupilRepository.findById(pupi.getId()).get();
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
    void updatePupil() {
        Pupil pup = pupilRepository.save(pupil);
        pup = pupilRepository.findById(pup.getId()).get();
        pup.setName("Leonardo");

        Pupil pupi = pupilRepository.save(pup);
        assertAll(
                () -> assertNotNull(pupi),
                () -> assertEquals(pupil.getId(), pupi.getId()),
                () -> assertEquals("Leonardo", pupi.getName()),
                () -> assertEquals(pupil.getEmail(), pupi.getEmail()),
                () -> assertEquals(pupil.getCreatedAt(), pupi.getCreatedAt())
        );
    }

    @Test
    @Order(5)
    void deletePupil() {
        Pupil pup = pupilRepository.save(pupil);
        pup = pupilRepository.findById(pup.getId()).get();

        pupilRepository.delete(pup);

        assertNull(pupilRepository.findById(pup.getId()).orElse(null));
    }
}
