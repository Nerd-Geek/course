package springbootapirestjava.controller.module;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import springbootapirestjava.dto.course.CourseDTO;
import springbootapirestjava.dto.module.ModuleDTO;
import springbootapirestjava.mapper.CourseMapper;
import springbootapirestjava.mapper.ModuleMapper;
import springbootapirestjava.model.Course;
import springbootapirestjava.model.Modulo;
import springbootapirestjava.model.NamberCourse;
import springbootapirestjava.repositories.CourseRepository;
import springbootapirestjava.repositories.ModuleRepository;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ModuloRestControllerMockMVCTest {
    private final ObjectMapper mapper = new ObjectMapper();
    @MockBean
    private final ModuleRepository moduleRepository;
    @MockBean
    private final ModuleMapper moduleMapper;
    private final Modulo modulo = Modulo.builder()
            .id("ec272c62-9d31-11ec-b909-0242ac120002")
            .name("nombre")
            .acronym("PSP")
            .createdAt(Date.from(Instant.now()))
            .build();
    private final ModuleDTO moduleDTO = ModuleDTO.builder()
            .id(modulo.getId())
            .name(modulo.getName())
            .acronym(modulo.getAcronym())
            .createAt(modulo.getCreatedAt())
            .build();
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JacksonTester<ModuleDTO> jsonCreateModuleDTO;
    @Autowired
    private JacksonTester<ModuleDTO> jsonModuleDTO;

    @Autowired
    public ModuloRestControllerMockMVCTest(ModuleRepository moduleRepository, ModuleMapper moduleMapper) {
        this.moduleRepository = moduleRepository;
        this.moduleMapper = moduleMapper;
    }

    @Test
    @Order(1)
    void findAllTest() throws Exception {
        Mockito.when(moduleRepository.findAll()).thenReturn(List.of(modulo));
        Mockito.when(moduleMapper.toDTO(List.of(modulo))).thenReturn(List.of(moduleDTO));

        mockMvc
                .perform(
                        get("/rest/modulo/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOi" +
                                        "JjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NzY2MzgsImV4cCI6MT" +
                                        "Y0NjY2MzAzOCwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE1JTiJ9.HIY1f7O_OWY9SDfnJHkgN" +
                                        "GvqxqbWuJutdF_cnA7ulkwYz-LrDpAFrsFd9MFSQCL7Ms87cqALVqXsV4z0cphRYg"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(modulo.getName())))
                .andExpect(jsonPath("$[0].acronym", is(modulo.getAcronym())))
                .andReturn();
        Mockito.verify(moduleRepository, Mockito.times(1)).findAll();
        Mockito.verify(moduleMapper, Mockito.times(1)).toDTO(List.of(modulo));
    }

    @Test
    @Order(2)
    void findByIdTest() throws Exception {
        Mockito.when(moduleRepository.findById(modulo.getId()))
                .thenReturn(Optional.of(modulo));
        Mockito.when(moduleMapper.toDTO(modulo)).thenReturn(moduleDTO);
        mockMvc.perform(
                        get("/rest/modulo/" + modulo.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOi" +
                                        "JjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NzY2MzgsImV4cCI6MT" +
                                        "Y0NjY2MzAzOCwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE1JTiJ9.HIY1f7O_OWY9SDfnJHkgN" +
                                        "GvqxqbWuJutdF_cnA7ulkwYz-LrDpAFrsFd9MFSQCL7Ms87cqALVqXsV4z0cphRYg"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(modulo.getName())))
                .andExpect(jsonPath("$.acronym", is(modulo.getAcronym())))
                .andReturn();
        Mockito.verify(moduleRepository, Mockito.times(1)).findById(modulo.getId());
        Mockito.verify(moduleMapper, Mockito.times(1)).toDTO(modulo);
    }

    @Test
    @Order(3)
    void findByExceptionTEst() throws Exception {
        Mockito.when(moduleRepository.findById(modulo.getId()))
                .thenReturn(Optional.empty());
        mockMvc.perform(
                        get("/rest/modulo/" + modulo.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOi" +
                                        "JjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NzY2MzgsImV4cCI6MT" +
                                        "Y0NjY2MzAzOCwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE1JTiJ9.HIY1f7O_OWY9SDfnJHkgN" +
                                        "GvqxqbWuJutdF_cnA7ulkwYz-LrDpAFrsFd9MFSQCL7Ms87cqALVqXsV4z0cphRYg"))
                .andExpect(status().isNotFound());
        Mockito.verify(moduleRepository, Mockito.times(1)).findById(modulo.getId());
    }

    @Test
    @Order(4)
    void updateTest() throws Exception {
        Mockito.when(moduleRepository.findById(modulo.getId())).thenReturn(Optional.of(modulo));
        Mockito.when(moduleRepository.save(modulo)).thenReturn(modulo);
        Mockito.when(moduleMapper.toDTO(modulo)).thenReturn(moduleDTO);
        var ser =  jsonModuleDTO.write(moduleDTO).getJson();

        mockMvc.perform(
                        put("/rest/modulo/" + modulo.getId())
                                .content(ser)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOi" +
                                        "JjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NzY2MzgsImV4cCI6MT" +
                                        "Y0NjY2MzAzOCwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE1JTiJ9.HIY1f7O_OWY9SDfnJHkgN" +
                                        "GvqxqbWuJutdF_cnA7ulkwYz-LrDpAFrsFd9MFSQCL7Ms87cqALVqXsV4z0cphRYg"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(modulo.getId())))
                .andExpect(jsonPath("$.acronym", is(modulo.getAcronym())))
                .andReturn();
        Mockito.verify(moduleRepository, Mockito.times(1)).findById(modulo.getId());
        Mockito.verify(moduleRepository, Mockito.times(1)).save(modulo);
        Mockito.verify(moduleMapper, Mockito.times(1)).toDTO(modulo);
    }
}
