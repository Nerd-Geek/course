package springbootapirestjava.repositories.modulo;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import springbootapirestjava.model.Modulo;
import springbootapirestjava.repositories.ModuleRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class ModuleRepositoryIntegrationTest {
    private final Modulo modulo = Modulo.builder()
            .id("eee369ba-e4d6-11ec-8fea-0242ac120002")
            .name("Acceso a datos")
            .acronym("AD")
            .createdAt(Date.from(Instant.now()))
            .build();

    @Autowired
    private ModuleRepository moduleRepository;

    @Test
    @Order(1)
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
    @Order(2)
    void getAllModule() {
        assertTrue(moduleRepository.findAll().size() > 0);
    }

    @Test
    @Order(3)
    void getModuleId() {
        Modulo modu = moduleRepository.save(modulo);
        Modulo mod = moduleRepository.findById(modu.getId()).get();
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
    void updateModule() {
        Modulo mod = moduleRepository.save(modulo);
        mod = moduleRepository.findById(mod.getId()).get();
        mod.setName("PSP");

        Modulo modu = moduleRepository.save(mod);
        assertAll(
                () -> assertNotNull(modu),
                () -> assertEquals(modulo.getId(), modu.getId()),
                () -> assertEquals("PSP", modu.getName()),
                () -> assertEquals(modulo.getAcronym(), modu.getAcronym()),
                () -> assertEquals(modulo.getCreatedAt(), modu.getCreatedAt())
        );
    }

    @Test
    @Order(5)
    void deleteModule() {
        Modulo mod = moduleRepository.save(modulo);
        mod = moduleRepository.findById(mod.getId()).get();

        moduleRepository.delete(mod);

        assertNull(moduleRepository.findById(mod.getId()).orElse(null));
    }
}
