package springbootapirestjava.repositories.pupil;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import springbootapirestjava.model.Pupil;
import springbootapirestjava.repositories.PupilRepository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PupilRepositoryMockTest {

    private final Pupil pupil = Pupil.builder()
            .id("dc7f075e-e4d9-11ec-8fea-0242ac120002")
            .name("Germán")
            .email("germa@german.com")
            .createdAt(LocalDate.now())
            .build();

    @MockBean
    private PupilRepository pupilRepository;

    @Test
    @Order(1)
    void save() {
        Mockito.when(pupilRepository.save(pupil)).thenReturn(pupil);
        Pupil pup = pupilRepository.save(pupil);
        assertAll(
                () -> assertNotNull(pup),
                () -> assertEquals(pupil.getId(), pup.getId()),
                () -> assertEquals(pupil.getName(), pup.getName()),
                () -> assertEquals(pupil.getEmail(), pup.getEmail()),
                () -> assertEquals(pupil.getCreatedAt(), pup.getCreatedAt())
        );

        Mockito.verify(pupilRepository, Mockito.times(1)).save(pupil);
    }

    @Test
    @Order(2)
    void findById() {
        Mockito.when(pupilRepository.findById(pupil.getId()))
                .thenReturn(java.util.Optional.of(pupil));
        Pupil pup = pupilRepository.findById(pupil.getId()).get();
        assertAll(
                () -> assertEquals(pupil ,pup),
                () -> assertEquals(pupil.getId(), pup.getId()),
                () -> assertEquals(pupil.getName(), pup.getName()),
                () -> assertEquals(pupil.getEmail(), pup.getEmail()),
                () -> assertEquals(pupil.getCreatedAt(), pup.getCreatedAt())
        );

        Mockito.verify(pupilRepository, Mockito.times(1)).findById(pupil.getId());
    }

    @Test
    @Order(3)
    void findAll() {
        Mockito.when(pupilRepository.findAll()).thenReturn(List.of(pupil));
        List<Pupil> pup = pupilRepository.findAll();
        assertAll(
                () -> assertEquals(List.of(pupil), pup),
                () -> assertEquals(pupil.getId(), pup.get(0).getId()),
                () -> assertEquals(pupil.getName(), pup.get(0).getName()),
                () -> assertEquals(pupil.getEmail(), pup.get(0).getEmail()),
                () -> assertEquals(pupil.getCreatedAt(), pup.get(0).getCreatedAt())
        );
        Mockito.verify(pupilRepository, Mockito.times(1)).findAll();
    }

    @Test
    @Order(4)
    void update() {
        Mockito.when(pupilRepository.save(pupil)).thenReturn(pupil);
        Pupil pup = pupilRepository.save(pupil);
        assertAll(
                () -> assertEquals(pupil ,pup),
                () -> assertEquals(pupil.getId(), pup.getId()),
                () -> assertEquals(pupil.getName(), pup.getName()),
                () -> assertEquals(pupil.getEmail(), pup.getEmail()),
                () -> assertEquals(pupil.getCreatedAt(), pup.getCreatedAt())
        );
        Mockito.verify(pupilRepository, Mockito.times(1)).save(pupil);
    }

    @Test
    @Order(5)
    public void delete() {
        Mockito.doNothing().when(pupilRepository).delete(pupil);
        pupilRepository.delete(pupil);
        Mockito.verify(pupilRepository, Mockito.times(1)).delete(pupil);
    }
}
