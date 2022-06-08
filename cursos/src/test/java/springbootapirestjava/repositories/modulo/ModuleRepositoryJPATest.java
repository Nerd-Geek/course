package springbootapirestjava.repositories.modulo;

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
import springbootapirestjava.model.Modulo;
import springbootapirestjava.repositories.ModuleRepository;

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
public class ModuleRepositoryJPATest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ModuleRepository moduleRepository;

    private final Modulo modulo = Modulo.builder()
            .id("eee369ba-e4d6-11ec-8fea-0242ac120002")
            .name("Acceso a datos")
            .acronym("AD")
            .createdAt(Date.from(Instant.now()))
            .build();

    @Test
    @Order(1)
    void getAllTest() {
        entityManager.persist(modulo);
        entityManager.flush();

        assertTrue(moduleRepository.findAll().size() > 0);
    }

    @Test
    @Order(2)
    void getByIdTest() {
        entityManager.persist(modulo);
        entityManager.flush();

        Modulo moduloFound = moduleRepository.findById(modulo.getId()).get();
        assertAll(
                () -> assertNotNull(moduloFound),
                () -> assertEquals(modulo.getId(), moduloFound.getId()),
                () -> assertEquals(modulo.getName(), moduloFound.getName()),
                () -> assertEquals(modulo.getAcronym(), moduloFound.getAcronym()),
                () -> assertEquals(modulo.getCreatedAt(), moduloFound.getCreatedAt())
        );
    }

    @Test
    @Order(3)
    void save() {
        Modulo mod = moduleRepository.save(modulo);
        assertAll(
                () -> assertNotNull(mod),
                () -> assertEquals(modulo.getId(), mod.getId()),
                () -> assertEquals(modulo.getName(), mod.getName()),
                () -> assertEquals(modulo.getAcronym(), mod.getAcronym()),
                () -> assertEquals(modulo.getCreatedAt(), mod.getCreatedAt())
        );
    }

    @Test
    @Order(4)
    void update() {
        entityManager.persist(modulo);
        entityManager.flush();
        Modulo moduloFound = moduleRepository.findById(modulo.getId()).get();
        moduloFound.setName("PSP");
        Modulo updated = moduleRepository.save(moduloFound);
        assertAll(
                () -> assertNotNull(updated),
                () -> assertEquals(modulo.getId(), updated.getId()),
                () -> assertEquals(modulo.getName(), updated.getName()),
                () -> assertEquals(modulo.getAcronym(), updated.getAcronym()),
                () -> assertEquals(modulo.getCreatedAt(), updated.getCreatedAt())
        );
    }

    @Test
    @Order(5)
    void delete() {
        entityManager.persist(modulo);
        entityManager.flush();
        moduleRepository.delete(modulo);
        Modulo mod = moduleRepository.findById(modulo.getId()).orElse(null);
        assertNull(mod);
    }
}
