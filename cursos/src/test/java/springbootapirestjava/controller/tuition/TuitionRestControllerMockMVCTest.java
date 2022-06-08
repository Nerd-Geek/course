package springbootapirestjava.controller.tuition;

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
import springbootapirestjava.dto.tuition.TuitionDTO;
import springbootapirestjava.mapper.TuitionMapper;
import springbootapirestjava.model.Tuition;
import springbootapirestjava.repositories.TuitionRepository;

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
public class TuitionRestControllerMockMVCTest {
    private final ObjectMapper mapper = new ObjectMapper();
    @MockBean
    private final TuitionRepository tuitionRepository;
    @MockBean
    private final TuitionMapper tuitionMapper;
    private final Tuition tuition = Tuition.builder()
            .id("ec272c62-9d31-11ec-b909-0242ac120002")
            .build();
    private final TuitionDTO tuitionDTO = TuitionDTO.builder()
            .id(tuition.getId())
            .build();
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JacksonTester<TuitionDTO> jsonCreateTuitionDTO;
    @Autowired
    private JacksonTester<TuitionDTO> jsonTuitionDTO;

    @Autowired
    public TuitionRestControllerMockMVCTest(TuitionRepository tuitionRepository, TuitionMapper tuitionMapper) {
        this.tuitionRepository = tuitionRepository;
        this.tuitionMapper = tuitionMapper;
    }

    @Test
    @Order(1)
    void findAllTest() throws Exception {
        Mockito.when(tuitionRepository.findAll()).thenReturn(List.of(tuition));
        Mockito.when(tuitionMapper.toDTO(List.of(tuition))).thenReturn(List.of(tuitionDTO));

        mockMvc
                .perform(
                        get("/rest/tuition/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOi" +
                                        "JjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NzY2MzgsImV4cCI6MT" +
                                        "Y0NjY2MzAzOCwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE1JTiJ9.HIY1f7O_OWY9SDfnJHkgN" +
                                        "GvqxqbWuJutdF_cnA7ulkwYz-LrDpAFrsFd9MFSQCL7Ms87cqALVqXsV4z0cphRYg"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(tuition.getId())))
                .andReturn();
        Mockito.verify(tuitionRepository, Mockito.times(1)).findAll();
        Mockito.verify(tuitionMapper, Mockito.times(1)).toDTO(List.of(tuition));
    }

    @Test
    @Order(2)
    void findByIdTest() throws Exception {
        Mockito.when(tuitionRepository.findById(tuition.getId()))
                .thenReturn(Optional.of(tuition));
        Mockito.when(tuitionMapper.toDTO(tuition)).thenReturn(tuitionDTO);
        mockMvc.perform(
                        get("/rest/tuition/" + tuition.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOi" +
                                        "JjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NzY2MzgsImV4cCI6MT" +
                                        "Y0NjY2MzAzOCwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE1JTiJ9.HIY1f7O_OWY9SDfnJHkgN" +
                                        "GvqxqbWuJutdF_cnA7ulkwYz-LrDpAFrsFd9MFSQCL7Ms87cqALVqXsV4z0cphRYg"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(tuition.getId())))
                .andReturn();
        Mockito.verify(tuitionRepository, Mockito.times(1)).findById(tuition.getId());
        Mockito.verify(tuitionMapper, Mockito.times(1)).toDTO(tuition);
    }

    @Test
    @Order(3)
    void findByExceptionTEst() throws Exception {
        Mockito.when(tuitionRepository.findById(tuition.getId()))
                .thenReturn(Optional.empty());
        mockMvc.perform(
                        get("/rest/tuition/" + tuition.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOi" +
                                        "JjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NzY2MzgsImV4cCI6MT" +
                                        "Y0NjY2MzAzOCwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE1JTiJ9.HIY1f7O_OWY9SDfnJHkgN" +
                                        "GvqxqbWuJutdF_cnA7ulkwYz-LrDpAFrsFd9MFSQCL7Ms87cqALVqXsV4z0cphRYg"))
                .andExpect(status().isNotFound());
        Mockito.verify(tuitionRepository, Mockito.times(1)).findById(tuition.getId());
    }

    @Test
    @Order(4)
    void deleteTest() throws Exception {
        Mockito.when(tuitionRepository.findById(tuition.getId()))
                .thenReturn(Optional.of(tuition));
        Mockito.when(tuitionMapper.toDTO(tuition)).thenReturn(tuitionDTO);
        Mockito.doNothing().when(tuitionRepository).delete(tuition);

        mockMvc.perform(
                        delete("/rest/tuition/" + tuition.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOi" +
                                        "JjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NzY2MzgsImV4cCI6MT" +
                                        "Y0NjY2MzAzOCwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE1JTiJ9.HIY1f7O_OWY9SDfnJHkgN" +
                                        "GvqxqbWuJutdF_cnA7ulkwYz-LrDpAFrsFd9MFSQCL7Ms87cqALVqXsV4z0cphRYg"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(tuition.getId())))
                .andReturn();

        Mockito.verify(tuitionRepository, Mockito.times(1))
                .findById(tuition.getId());
        Mockito.verify(tuitionRepository, Mockito.times(1))
                .delete(tuition);
        Mockito.verify(tuitionMapper, Mockito.times(1))
                .toDTO(tuition);
    }

    @Test
    @Order(5)
    void deleteExceptionTest() throws Exception {
        Mockito.when(tuitionRepository.findById(tuition.getId()))
                .thenReturn(Optional.empty());
        mockMvc.perform(
                        delete("/rest/tuition/" + tuition.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOi" +
                                        "JjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NzY2MzgsImV4cCI6MT" +
                                        "Y0NjY2MzAzOCwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE1JTiJ9.HIY1f7O_OWY9SDfnJHkgN" +
                                        "GvqxqbWuJutdF_cnA7ulkwYz-LrDpAFrsFd9MFSQCL7Ms87cqALVqXsV4z0cphRYg"))
                .andExpect(status().isNotFound());
        Mockito.verify(tuitionRepository, Mockito.times(1)).findById(tuition.getId());
    }

    @Test
    @Order(6)
    void updateTest() throws Exception {
        Mockito.when(tuitionRepository.findById(tuition.getId())).thenReturn(Optional.of(tuition));
        Mockito.when(tuitionRepository.save(tuition)).thenReturn(tuition);
        Mockito.when(tuitionMapper.toDTO(tuition)).thenReturn(tuitionDTO);
        var ser =  jsonTuitionDTO.write(tuitionDTO).getJson();

        mockMvc.perform(
                        put("/rest/tuition/" + tuition.getId())
                                .content(ser)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOi" +
                                        "JjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NzY2MzgsImV4cCI6MT" +
                                        "Y0NjY2MzAzOCwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE1JTiJ9.HIY1f7O_OWY9SDfnJHkgN" +
                                        "GvqxqbWuJutdF_cnA7ulkwYz-LrDpAFrsFd9MFSQCL7Ms87cqALVqXsV4z0cphRYg"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(tuition.getId())))
                .andReturn();
        Mockito.verify(tuitionRepository, Mockito.times(1)).findById(tuition.getId());
        Mockito.verify(tuitionRepository, Mockito.times(1)).save(tuition);
        Mockito.verify(tuitionMapper, Mockito.times(1)).toDTO(tuition);
    }
}
