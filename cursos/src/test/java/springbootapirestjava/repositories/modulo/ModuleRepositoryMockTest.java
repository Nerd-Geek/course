package springbootapirestjava.repositories.modulo;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import springbootapirestjava.model.Modulo;
import springbootapirestjava.repositories.ModuleRepository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ModuleRepositoryMockTest {

    private final Modulo modulo = Modulo.builder()
            .id("eee369ba-e4d6-11ec-8fea-0242ac120002")
            .name("Acceso a datos")
            .acronym("AD")
            .createdAt(LocalDate.now())
            .build();

    @MockBean
    private ModuleRepository moduleRepository;

    @Test
    @Order(1)
    void save() {
        Mockito.when(moduleRepository.save(modulo)).thenReturn(modulo);
        Modulo mod = moduleRepository.save(modulo);
        assertAll(
                () -> assertNotNull(mod),
                () -> assertEquals(modulo.getId(), mod.getId()),
                () -> assertEquals(modulo.getName(), mod.getName()),
                () -> assertEquals(modulo.getAcronym(), mod.getAcronym()),
                () -> assertEquals(modulo.getCreatedAt(), mod.getCreatedAt())
        );

        Mockito.verify(moduleRepository, Mockito.times(1)).save(modulo);
    }

    @Test
    @Order(2)
    void findById() {
        Mockito.when(moduleRepository.findById(modulo.getId()))
                .thenReturn(java.util.Optional.of(modulo));
        Modulo mod = moduleRepository.findById(modulo.getId()).get();
        assertAll(
                () -> assertEquals(modulo ,mod),
                () -> assertEquals(modulo.getId(), mod.getId()),
                () -> assertEquals(modulo.getName(), mod.getName()),
                () -> assertEquals(modulo.getAcronym(), mod.getAcronym()),
                () -> assertEquals(modulo.getCreatedAt(), mod.getCreatedAt())
        );

        Mockito.verify(moduleRepository, Mockito.times(1)).findById(modulo.getId());
    }

    @Test
    @Order(3)
    void findAll() {
        Mockito.when(moduleRepository.findAll()).thenReturn(List.of(modulo));
        List<Modulo> mod = moduleRepository.findAll();
        assertAll(
                () -> assertEquals(List.of(modulo), mod),
                () -> assertEquals(modulo.getId(), mod.get(0).getId()),
                () -> assertEquals(modulo.getName(), mod.get(0).getName()),
                () -> assertEquals(modulo.getAcronym(), mod.get(0).getAcronym()),
                () -> assertEquals(modulo.getCreatedAt(), mod.get(0).getCreatedAt())
        );
        Mockito.verify(moduleRepository, Mockito.times(1)).findAll();
    }

    @Test
    @Order(4)
    void update() {
        Mockito.when(moduleRepository.save(modulo)).thenReturn(modulo);
        Modulo mod = moduleRepository.save(modulo);
        assertAll(
                () -> assertEquals(modulo ,mod),
                () -> assertEquals(modulo.getId(), mod.getId()),
                () -> assertEquals(modulo.getName(), mod.getName()),
                () -> assertEquals(modulo.getAcronym(), mod.getAcronym()),
                () -> assertEquals(modulo.getCreatedAt(), mod.getCreatedAt())
        );
        Mockito.verify(moduleRepository, Mockito.times(1)).save(modulo);
    }

    @Test
    @Order(5)
    public void delete() {
        Mockito.doNothing().when(moduleRepository).delete(modulo);
        moduleRepository.delete(modulo);
        Mockito.verify(moduleRepository, Mockito.times(1)).delete(modulo);
    }
}
