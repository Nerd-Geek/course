package springbootapirestjava.repositories.tuition;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import springbootapirestjava.model.Tuition;
import springbootapirestjava.repositories.TuitionRepository;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class TuitionRepositoryIntegrationTest {
    private final Tuition tuition = Tuition.builder()
            .id("1cf7f302-e4e1-11ec-8fea-0242ac120002")
            .pupils(new HashSet<>())
            .build();

    @Autowired
    private TuitionRepository tuitionRepository;

    @Test
    @Order(1)
    void save() {
        Tuition tui = tuitionRepository.save(tuition);

        assertAll(
                () -> assertNotNull(tui),
                () -> assertEquals(tuition.getId(), tui.getId())
        );
    }

    @Test
    @Order(2)
    void getAllTuition() {
        assertTrue(tuitionRepository.findAll().size() > 0);
    }


    @Test
    @Order(3)
    void getTuitionId() {
        Tuition tuit = tuitionRepository.save(tuition);
        Tuition tui = tuitionRepository.findById(tuit.getId()).get();
        assertAll(
                () -> assertNotNull(tui),
                () -> assertEquals(tuition.getId(), tui.getId())
        );
    }

    @Test
    @Order(4)
    void updateTuition() {
        Tuition tui = tuitionRepository.save(tuition);
        tui = tuitionRepository.findById(tui.getId()).get();
        tui.setId("847292bc-e4e1-11ec-8fea-0242ac120002");

        Tuition tuit = tuitionRepository.save(tui);
        assertAll(
                () -> assertNotNull(tuit),
                () -> assertEquals("847292bc-e4e1-11ec-8fea-0242ac120002", tuit.getId())
        );
    }

    @Test
    @Order(5)
    void deleteTuition() {
        Tuition tui = tuitionRepository.save(tuition);
        tui = tuitionRepository.findById(tui.getId()).get();

        tuitionRepository.delete(tui);

        assertNull(tuitionRepository.findById(tui.getId()).orElse(null));
    }
}
