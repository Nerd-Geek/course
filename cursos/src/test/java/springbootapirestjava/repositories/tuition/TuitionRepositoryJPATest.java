package springbootapirestjava.repositories.tuition;

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
import springbootapirestjava.model.Tuition;
import springbootapirestjava.repositories.TuitionRepository;

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
public class TuitionRepositoryJPATest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TuitionRepository tuitionRepository;

    private final Tuition tuition = Tuition.builder()
            .id("1cf7f302-e4e1-11ec-8fea-0242ac120002")
            .pupils(new HashSet<>())
            .build();

    @Test
    @Order(1)
    void getAllTest() {
        entityManager.persist(tuition);
        entityManager.flush();

        assertTrue(tuitionRepository.findAll().size() > 0);
    }

    @Test
    @Order(2)
    void getByIdTest() {
        entityManager.persist(tuition);
        entityManager.flush();

        Tuition tuitionFound = tuitionRepository.findById(tuition.getId()).get();
        assertAll(
                () -> assertNotNull(tuitionFound),
                () -> assertEquals(tuition.getId(), tuitionFound.getId())
        );
    }

    @Test
    @Order(3)
    void save() {
        Tuition tui = tuitionRepository.save(tuition);
        assertAll(
                () -> assertNotNull(tui),
                () -> assertEquals(tuition.getId(), tui.getId())
        );
    }

    @Test
    @Order(4)
    void update() {
        entityManager.persist(tuition);
        entityManager.flush();
        Tuition tuitionFound = tuitionRepository.findById(tuition.getId()).get();
        tuitionFound.setId("847292bc-e4e1-11ec-8fea-0242ac120002");
        Tuition updated = tuitionRepository.save(tuitionFound);
        assertAll(
                () -> assertNotNull(updated),
                () -> assertEquals(tuition.getId(), updated.getId())
        );
    }

    @Test
    @Order(5)
    void delete() {
        entityManager.persist(tuition);
        entityManager.flush();
        tuitionRepository.delete(tuition);
        Tuition tui = tuitionRepository.findById(tuition.getId()).orElse(null);
        assertNull(tui);
    }
}
