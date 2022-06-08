package springbootapirestjava.controller.module;

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
import springbootapirestjava.controller.ModuloController;
import springbootapirestjava.dto.module.ModuleDTO;
import springbootapirestjava.exceptions.module.ModuleNotFountException;
import springbootapirestjava.mapper.ModuleMapper;
import springbootapirestjava.model.Modulo;
import springbootapirestjava.repositories.ModuleRepository;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ModuleControllerMockTest {
    @MockBean
    private final ModuleRepository moduleRepository;
    @MockBean
    private final ModuleMapper moduleMapper;

    private final Modulo modulo = Modulo.builder()
            .id("4203de85-4295-4ff4-b776-033faec08005")
            .name("Acceso a datos")
            .acronym("AD")
            .createdAt(Date.from(Instant.now()))
            .build();

    @InjectMocks
    private ModuloController moduloController;
    @Autowired
    public ModuleControllerMockTest(ModuleRepository moduleRepository, ModuleMapper moduleMapper) {
        this.moduleRepository = moduleRepository;
        this.moduleMapper = moduleMapper;
    }

    @Test
    @Order(1)
    void getAllTestMock() {
        ModuleDTO moduleDTO = ModuleDTO.builder()
                .id(modulo.getId())
                .name(modulo.getName())
                .acronym(modulo.getAcronym())
                .build();

        Mockito.when(moduleRepository.findAll())
                .thenReturn(List.of(modulo));

        Mockito.when(moduleMapper.toDTO(List.of(modulo))).thenReturn(List.of(moduleDTO));

        var response = moduloController.getAll(
                java.util.Optional.empty()
        );
        var res = response.getBody();

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.get(0).getId(), modulo.getId()),
                () -> assertEquals(res.get(0).getName(), modulo.getName()),
                () -> assertEquals(res.get(0).getAcronym(), modulo.getAcronym())
        );
    }

    @Test
    @Order(2)
    void getByIdTestMock() {
        ModuleDTO moduleDTO = ModuleDTO.builder()
                .id(modulo.getId())
                .name(modulo.getName())
                .acronym(modulo.getAcronym())
                .build();

        Mockito.when(moduleRepository.findById(modulo.getId()))
                .thenReturn(Optional.of(modulo));

        Mockito.when(moduleMapper.toDTO(modulo)).thenReturn(moduleDTO);

        var response = moduloController.findById(modulo.getId());
        var res = response.getBody();

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.getId(), modulo.getId()),
                () -> assertEquals(res.getName(), modulo.getName()),
                () -> assertEquals(res.getAcronym(), modulo.getAcronym())
        );

        Mockito.verify(moduleRepository, Mockito.times(1)).findById(modulo.getId());
        Mockito.verify(moduleMapper, Mockito.times(1)).toDTO(modulo);
    }

    @Test
    @Order(3)
    void findByIdException() {
        Mockito.when(moduleRepository.findById(modulo.getId())).thenReturn(Optional.empty());

        Exception ex = assertThrows(ModuleNotFountException.class, () -> {
            moduloController.findById(modulo.getId());
        });

        assertTrue(ex.getMessage().contains("service"));

        Mockito.verify(moduleRepository, Mockito.times(1))
                .findById(modulo.getId());
    }

    @Test
    @Order(4)
    void saveTestMock() {
        ModuleDTO moduleCreateDTO = ModuleDTO.builder()
                .id(modulo.getId())
                .name(modulo.getName())
                .acronym(modulo.getAcronym())
                .build();


        ModuleDTO moduleDTO = ModuleDTO.builder()
                .id(modulo.getId())
                .name(modulo.getName())
                .acronym(modulo.getAcronym())
                .build();

        Mockito.when(moduleRepository.save(modulo))
                .thenReturn(modulo);

        Mockito.when(moduleMapper.toModel(moduleCreateDTO))
                .thenReturn(modulo);

        Mockito.when(moduleMapper.toDTO(modulo)).thenReturn(moduleDTO);

        var response = moduloController.postModule(moduleCreateDTO);
        var res = response.getBody();

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.getId(), modulo.getId()),
                () -> assertEquals(res.getName(), modulo.getName()),
                () -> assertEquals(res.getAcronym(), modulo.getAcronym())
        );

        Mockito.verify(moduleRepository, Mockito.times(1))
                .save(modulo);
        Mockito.verify(moduleMapper, Mockito.times(1))
                .toModel(moduleCreateDTO);
        Mockito.verify(moduleMapper, Mockito.times(1))
                .toDTO(modulo);
    }

    @Test
    @Order(5)
    void updateTestMock() {
        ModuleDTO moduleDTO = ModuleDTO.builder()
                .id(modulo.getId())
                .name(modulo.getName())
                .acronym(modulo.getAcronym())
                .build();

        Mockito.when(moduleRepository.findById(modulo.getId()))
                .thenReturn(java.util.Optional.of(modulo));

        Mockito.when(moduleRepository.save(modulo))
                .thenReturn(modulo);

        Mockito.when(moduleMapper.toDTO(modulo)).thenReturn(moduleDTO);

        var response = moduloController.putModule(modulo.getId(), moduleDTO);
        var res = response.getBody();

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.getId(), modulo.getId()),
                () -> assertEquals(res.getName(), modulo.getName()),
                () -> assertEquals(res.getAcronym(), modulo.getAcronym())
        );

        Mockito.verify(moduleRepository, Mockito.times(1))
                .findById(modulo.getId());
        Mockito.verify(moduleRepository, Mockito.times(1))
                .save(modulo);
        Mockito.verify(moduleMapper, Mockito.times(1))
                .toDTO(modulo);
    }
}
