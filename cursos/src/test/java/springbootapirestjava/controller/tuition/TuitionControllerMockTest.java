
package springbootapirestjava.controller.tuition;

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
import springbootapirestjava.controller.TuitionController;
import springbootapirestjava.dto.tuition.TuitionDTO;
import springbootapirestjava.exceptions.tuition.TuitionNotFountException;
import springbootapirestjava.mapper.TuitionMapper;
import springbootapirestjava.model.Tuition;
import springbootapirestjava.repositories.TuitionRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TuitionControllerMockTest {
    @MockBean
    private final TuitionRepository tuitionRepository;
    @MockBean
    private final TuitionMapper tuitionMapper;
    private final Tuition tuition = Tuition.builder()
            .id("ec272c62-9d31-11ec-b909-0242ac120002")
            .build();
    @InjectMocks
    private TuitionController tuitionController;
    @Autowired
    public TuitionControllerMockTest(TuitionRepository tuitionRepository, TuitionMapper tuitionMapper) {
        this.tuitionRepository = tuitionRepository;
        this.tuitionMapper = tuitionMapper;
    }

    @Test
    @Order(1)
    void getAllTestMock() {
        TuitionDTO tuitionDTO = TuitionDTO.builder()
                .id(tuition.getId())
                .build();

        Mockito.when(tuitionRepository.findAll())
                .thenReturn(List.of(tuition));

        Mockito.when(tuitionMapper.toDTO(List.of(tuition))).thenReturn(List.of(tuitionDTO));

        var response = tuitionController.getAll(
                java.util.Optional.empty()
        );
        var res = response.getBody();

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.get(0).getId(), tuition.getId())
        );
    }

    @Test
    @Order(2)
    void getByIdTestMock() {
        TuitionDTO tuitionDTO = TuitionDTO.builder()
                .id(tuition.getId())
                .build();

        Mockito.when(tuitionRepository.findById(tuition.getId()))
                .thenReturn(Optional.of(tuition));

        Mockito.when(tuitionMapper.toDTO(tuition)).thenReturn(tuitionDTO);

        var response = tuitionController.findById(tuition.getId());
        var res = response.getBody();

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.getId(), tuition.getId())
        );

        Mockito.verify(tuitionRepository, Mockito.times(1)).findById(tuition.getId());
        Mockito.verify(tuitionMapper, Mockito.times(1)).toDTO(tuition);
    }

    @Test
    @Order(3)
    void findByIdException() {
        Mockito.when(tuitionRepository.findById(tuition.getId())).thenReturn(Optional.empty());

        Exception ex = assertThrows(TuitionNotFountException.class, () -> {
            tuitionController.findById(tuition.getId());
        });

        assertTrue(ex.getMessage().contains("service"));

        Mockito.verify(tuitionRepository, Mockito.times(1))
                .findById(tuition.getId());
    }

    @Test
    @Order(4)
    void saveTestMock() {
        TuitionDTO tuitionCreateDTO = TuitionDTO.builder()
                .id(tuition.getId())
                .build();

        TuitionDTO tuitionDTO = TuitionDTO.builder()
                .id(tuition.getId())
                .build();

        Mockito.when(tuitionRepository.save(tuition))
                .thenReturn(tuition);

        Mockito.when(tuitionMapper.toModel(tuitionCreateDTO))
                .thenReturn(tuition);

        Mockito.when(tuitionMapper.toDTO(tuition)).thenReturn(tuitionDTO);

        var response = tuitionController.postTuition(tuitionCreateDTO);
        var res = response.getBody();

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.getId(), tuition.getId())
        );

        Mockito.verify(tuitionRepository, Mockito.times(1))
                .save(tuition);
        Mockito.verify(tuitionMapper, Mockito.times(1))
                .toModel(tuitionCreateDTO);
        Mockito.verify(tuitionMapper, Mockito.times(1))
                .toDTO(tuition);
    }

    @Test
    @Order(5)
    void updateTestMock() {
        TuitionDTO tuitionDTO = TuitionDTO.builder()
                .id(tuition.getId())
                .build();

        Mockito.when(tuitionRepository.findById(tuition.getId()))
                .thenReturn(java.util.Optional.of(tuition));

        Mockito.when(tuitionRepository.save(tuition))
                .thenReturn(tuition);

        Mockito.when(tuitionMapper.toDTO(tuition)).thenReturn(tuitionDTO);

        var response = tuitionController.putTuition(tuition.getId(), tuitionDTO);
        var res = response.getBody();

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.getId(), tuition.getId())
        );

        Mockito.verify(tuitionRepository, Mockito.times(1))
                .findById(tuition.getId());
        Mockito.verify(tuitionRepository, Mockito.times(1))
                .save(tuition);
        Mockito.verify(tuitionMapper, Mockito.times(1))
                .toDTO(tuition);
    }

    @Test
    @Order(6)
    void deleteTestMock() {
        TuitionDTO tuitionDTO = TuitionDTO.builder()
                .id(tuition.getId())
                .build();

        Mockito.when(tuitionRepository.findById(tuition.getId()))
                .thenReturn(java.util.Optional.of(tuition));

        Mockito.when(tuitionMapper.toDTO(tuition)).thenReturn(tuitionDTO);

        var response = tuitionController.delete(tuition.getId());
        var res = response.getBody();

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.getId(), tuition.getId())
        );

        Mockito.verify(tuitionRepository, Mockito.times(1))
                .findById(tuition.getId());
        Mockito.verify(tuitionMapper, Mockito.times(1))
                .toDTO(tuition);
    }
}
