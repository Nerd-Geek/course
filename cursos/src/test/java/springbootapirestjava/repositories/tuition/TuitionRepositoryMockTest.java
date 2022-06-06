package springbootapirestjava.repositories.tuition;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import springbootapirestjava.model.Tuition;
import springbootapirestjava.repositories.TuitionRepository;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TuitionRepositoryMockTest {
    private final Tuition tuition = Tuition.builder()
            .id("1cf7f302-e4e1-11ec-8fea-0242ac120002")
            .pupils(new HashSet<>())
            .build();

    @MockBean
    private TuitionRepository tuitionRepository;

    @Test
    @Order(1)
    void save() {
        Mockito.when(tuitionRepository.save(tuition)).thenReturn(tuition);
        Tuition tui = tuitionRepository.save(tuition);
        assertAll(
                () -> assertNotNull(tui),
                () -> assertEquals(tuition.getId(), tui.getId())
        );

        Mockito.verify(tuitionRepository, Mockito.times(1)).save(tuition);
    }

    @Test
    @Order(2)
    void findById() {
        Mockito.when(tuitionRepository.findById(tuition.getId()))
                .thenReturn(java.util.Optional.of(tuition));
        Tuition tui = tuitionRepository.findById(tuition.getId()).get();
        assertAll(
                () -> assertEquals(tuition, tui),
                () -> assertEquals(tuition.getId(), tui.getId())
        );

        Mockito.verify(tuitionRepository, Mockito.times(1)).findById(tuition.getId());
    }

    @Test
    @Order(3)
    void findAll() {
        Mockito.when(tuitionRepository.findAll()).thenReturn(List.of(tuition));
        List<Tuition> tui = tuitionRepository.findAll();
        assertAll(
                () -> assertEquals(List.of(tuition), tui),
                () -> assertEquals(tuition.getId(), tui.get(0).getId())
        );
        Mockito.verify(tuitionRepository, Mockito.times(1)).findAll();
    }

    @Test
    @Order(4)
    void update() {
        Mockito.when(tuitionRepository.save(tuition)).thenReturn(tuition);
        Tuition tui = tuitionRepository.save(tuition);
        assertAll(
                () -> assertEquals(tuition, tui),
                () -> assertEquals(tuition.getId(), tui.getId())
        );
        Mockito.verify(tuitionRepository, Mockito.times(1)).save(tuition);
    }

    @Test
    @Order(5)
    public void delete() {
        Mockito.doNothing().when(tuitionRepository).delete(tuition);
        tuitionRepository.delete(tuition);
        Mockito.verify(tuitionRepository, Mockito.times(1)).delete(tuition);
    }
}
