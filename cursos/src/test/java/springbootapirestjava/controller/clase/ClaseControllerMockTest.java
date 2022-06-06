package springbootapirestjava.controller.clase;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import springbootapirestjava.controller.ClaseController;
import springbootapirestjava.dto.clase.ClaseDTO;
import springbootapirestjava.exceptions.clase.ClaseBadRequestException;
import springbootapirestjava.exceptions.clase.ClaseNotFountException;
import springbootapirestjava.mapper.ClaseMapper;
import springbootapirestjava.model.Clase;
import springbootapirestjava.repositories.ClaseRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClaseControllerMockTest {

    @MockBean
    private final ClaseRepository claseRepository;
    @MockBean
    private final ClaseMapper claseMapper;
    private final Clase clase = Clase.builder()
            .id("a4594ad4-e4ba-11ec-8fea-0242ac120002")
            .clasification(6.0)
            .modules(new HashSet<>())
            .pupils(new HashSet<>())
            .build();
    @InjectMocks
    private ClaseController claseController;

    @Autowired
    public ClaseControllerMockTest(ClaseRepository claseRepository, ClaseMapper claseMapper) {
        this.claseRepository = claseRepository;
        this.claseMapper = claseMapper;
    }

    @Test
    @Order(1)
    void getAllTestMock() {
        ClaseDTO claseDTO = ClaseDTO.builder()
                .id(clase.getId())
                .clasification(clase.getClasification())
                .build();

        Mockito.when(claseRepository.findAll())
                .thenReturn(List.of(clase));

        Mockito.when(claseMapper.toDTO(List.of(clase))).thenReturn(List.of(claseDTO));

        var response = claseController.findAll(
                java.util.Optional.empty()
        );
        var res = response.getBody();

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.get(0).getId(), clase.getId()),
                () -> assertEquals(res.get(0).getClasification(), clase.getClasification())
        );
    }

    @Test
    @Order(2)
    void getByIdTestMock() {
        ClaseDTO claseDTO = ClaseDTO.builder()
                .id(clase.getId())
                .clasification(clase.getClasification())
                .build();

        Mockito.when(claseRepository.findById(clase.getId()))
                .thenReturn(Optional.of(clase));

        Mockito.when(claseMapper.toDTO(clase)).thenReturn(claseDTO);

        var response = claseController.findById(clase.getId());
        var res = response.getBody();

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.getId(), clase.getId()),
                () -> assertEquals(res.getClasification(), clase.getClasification())
        );

        Mockito.verify(claseRepository, Mockito.times(1)).findById(clase.getId());
        Mockito.verify(claseMapper, Mockito.times(1)).toDTO(clase);
    }

    @Test
    @Order(3)
    void findByIdException() {
        Mockito.when(claseRepository.findById(clase.getId())).thenReturn(Optional.empty());

        Exception ex = assertThrows(ClaseNotFountException.class, () -> {
            claseController.findById(clase.getId());
        });

        assertTrue(ex.getMessage().contains("service"));

        Mockito.verify(claseRepository, Mockito.times(1))
                .findById(clase.getId());
    }

    @Test
    @Order(4)
    void saveTestMock() {
        ClaseDTO claseCreateDTO = ClaseDTO.builder()
                .id(clase.getId())
                .clasification(clase.getClasification())
                .build();

        ClaseDTO claseDTO = ClaseDTO.builder()
                .id(clase.getId())
                .clasification(clase.getClasification())
                .build();

        Mockito.when(claseRepository.save(clase))
                .thenReturn(clase);

        Mockito.when(claseMapper.toModel(claseCreateDTO))
                .thenReturn(clase);

        Mockito.when(claseMapper.toDTO(clase)).thenReturn(claseDTO);

        var response = claseController.postClase(claseCreateDTO);
        var res = response.getBody();

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.getId(), clase.getId()),
                () -> assertEquals(res.getClasification(), clase.getClasification())
        );

        Mockito.verify(claseRepository, Mockito.times(1))
                .save(clase);
        Mockito.verify(claseMapper, Mockito.times(1))
                .toModel(claseCreateDTO);
        Mockito.verify(claseMapper, Mockito.times(1))
                .toDTO(clase);
    }

    @Test
    @Order(5)
    void updateTestMock() {
        ClaseDTO claseDTO = ClaseDTO.builder()
                .id(clase.getId())
                .clasification(clase.getClasification())
                .build();

        Mockito.when(claseRepository.findById(clase.getId()))
                .thenReturn(java.util.Optional.of(clase));

        Mockito.when(claseRepository.save(clase))
                .thenReturn(clase);

        Mockito.when(claseMapper.toDTO(clase)).thenReturn(claseDTO);

        var response = claseController.putClase(clase.getId(), claseDTO);
        var res = response.getBody();

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.getId(), clase.getId()),
                () -> assertEquals(res.getClasification(), clase.getClasification())
        );

        Mockito.verify(claseRepository, Mockito.times(1))
                .findById(clase.getId());
        Mockito.verify(claseRepository, Mockito.times(1))
                .save(clase);
        Mockito.verify(claseMapper, Mockito.times(1))
                .toDTO(clase);
    }
}
