package springbootapirestjava.repositories.clase;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.filter.TypeExcludeFilters;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import org.springframework.boot.test.context.SpringBootTest;
import springbootapirestjava.model.Clase;
import springbootapirestjava.repositories.ClaseRepository;

import javax.transaction.Transactional;

import java.util.HashSet;

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
public class ClaseRepositoryJPATest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ClaseRepository claseRepository;

    private final Clase clase = Clase.builder()
            .id("a4594ad4-e4ba-11ec-8fea-0242ac120002")
            .clasification(6.0)
            .modules(new HashSet<>())
            .pupils(new HashSet<>())
            .build();

    @Test
    @Order(1)
    void getALlTest() {
        entityManager.persist(clase);
        entityManager.flush();

        assertTrue(claseRepository.findAll().size() > 0);
    }

    @Test
    @Order(2)
    void getByIdTest() {
        entityManager.persist(clase);
        entityManager.flush();

        Clase claseFound = claseRepository.findById(clase.getId()).get();
        assertAll(
                () -> assertNotNull(claseFound),
                () -> assertEquals(clase.getId(), claseFound.getId()),
                () -> assertEquals(clase.getClasification(), claseFound.getClasification())
        );
    }

    @Test
    @Order(3)
    void save() {
        Clase clas = claseRepository.save(clase);
        assertAll(
                () -> assertNotNull(clas),
                () -> assertEquals(clase.getId(), clas.getId()),
                () -> assertEquals(clase.getClasification(), clas.getClasification())
        );
    }

    @Test
    @Order(4)
    void update() {
        entityManager.persist(clase);
        entityManager.flush();
        Clase claseFound = claseRepository.findById(clase.getId()).get();
        claseFound.setClasification(7.0);
        Clase updated = claseRepository.save(claseFound);
        assertAll(
                () -> assertNotNull(updated),
                () -> assertEquals(clase.getId(), updated.getId()),
                () -> assertEquals(clase.getClasification(), updated.getClasification())
        );
    }

    @Test
    @Order(6)
    void delete() {
        entityManager.persist(clase);
        entityManager.flush();
        claseRepository.delete(clase);
        Clase clas = claseRepository.findById(clase.getId()).orElse(null);
        assertNull(clas);
    }
}
