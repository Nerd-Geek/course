package springbootapirestjava.repositories.clase;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import springbootapirestjava.model.Clase;
import springbootapirestjava.repositories.ClaseRepository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class ClaseRepositoryIntegrationJava {
    private final Clase clase = Clase.builder()
            .id("a4594ad4-e4ba-11ec-8fea-0242ac120002")
            .clasification(6.0)
            .modules(new HashSet<>())
            .pupils(new HashSet<>())
            .build();

    @Autowired
    private ClaseRepository claseRepository;

    @Test
    @Order(1)
    void save() {
        Clase clas = claseRepository.save(clase);

        assertAll(
                () -> assertNotNull(clas),
                () -> assertEquals(clase.getId(), clas.getId()),
                () -> assertEquals(clase.getClasification(), clas.getClasification())
        );
    }

    @Test
    @Order(2)
    void getAllClase() {
        assertTrue(claseRepository.findAll().size() > 0);
    }

    @Test
    @Order(3)
    void getClaseId() {
        Clase clas = claseRepository.save(clase);
        Clase clase1 = claseRepository.findById(clas.getId()).get();
        assertAll(
                () -> assertNotNull(clase1),
                () -> assertEquals(clase.getId(), clase1.getId()),
                () -> assertEquals(clase.getClasification(), clase1.getClasification())
        );
    }

    @Test
    @Order(4)
    void updateClase() {
        Clase clas = claseRepository.save(clase);
        clas = claseRepository.findById(clas.getId()).get();
        clas.setClasification(7.0);

        Clase clase1 = claseRepository.save(clas);
        assertAll(
                () -> assertNotNull(clase1),
                () -> assertEquals(7.0, clase1.getClasification()),
                () -> assertEquals(clase.getId(), clase1.getId())
        );
    }

    @Test
    @Order(5)
    void deleteClase() {
        Clase clas = claseRepository.save(clase);
        clas = claseRepository.findById(clas.getId()).get();

        claseRepository.delete(clas);

        assertNull(claseRepository.findById(clas.getId()).orElse(null));
    }
}
