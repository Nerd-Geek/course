package springbootapirestjava.repositories.clase;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import springbootapirestjava.model.Clase;
import springbootapirestjava.repositories.ClaseRepository;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClaseRepositoryMockTest {
    private final Clase clase = Clase.builder()
            .id("a4594ad4-e4ba-11ec-8fea-0242ac120002")
            .clasification(6.0)
            .modules(new HashSet<>())
            .pupils(new HashSet<>())
            .build();

    @MockBean
    private ClaseRepository claseRepository;

    @Test
    @Order(1)
    void save() {
        Mockito.when(claseRepository.save(clase)).thenReturn(clase);
        Clase clas = claseRepository.save(clase);
        assertAll(
                () -> assertNotNull(clas),
                () -> assertEquals(clase.getId(), clas.getId()),
                () -> assertEquals(clase.getClasification(), clas.getClasification())
        );

        Mockito.verify(claseRepository, Mockito.times(1)).save(clase);
    }

    @Test
    @Order(2)
    void findById() {
        Mockito.when(claseRepository.findById(clase.getId()))
                .thenReturn(java.util.Optional.of(clase));
        Clase clas = claseRepository.findById(clase.getId()).get();
        assertAll(
                () -> assertEquals(clase, clas),
                () -> assertEquals(clase.getId(), clas.getId()),
                () -> assertEquals(clase.getClasification(), clas.getClasification())
        );

        Mockito.verify(claseRepository, Mockito.times(1)).findById(clase.getId());
    }

    @Test
    @Order(3)
    void findAll() {
        Mockito.when(claseRepository.findAll()).thenReturn(List.of(clase));
        List<Clase> clas = claseRepository.findAll();
        assertAll(
                () -> assertEquals(List.of(clase), clas),
                () -> assertEquals(clase.getId(), clas.get(0).getId()),
                () -> assertEquals(clase.getClasification(), clas.get(0).getClasification())
        );
        Mockito.verify(claseRepository, Mockito.times(1)).findAll();
    }

    @Test
    @Order(4)
    void update() {
        Mockito.when(claseRepository.save(clase)).thenReturn(clase);
        Clase clas = claseRepository.save(clase);
        assertAll(
                () -> assertEquals(clase, clas),
                () -> assertEquals(clase.getId(), clas.getId()),
                () -> assertEquals(clase.getClasification(), clas.getClasification())
        );
        Mockito.verify(claseRepository, Mockito.times(1)).save(clase);
    }

    @Test
    @Order(5)
    public void delete() {
        Mockito.doNothing().when(claseRepository).delete(clase);
        claseRepository.delete(clase);
        Mockito.verify(claseRepository, Mockito.times(1)).delete(clase);
    }
}
