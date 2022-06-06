package springbootapirestjava.controller.clase;

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
import springbootapirestjava.dto.clase.ClaseDTO;
import springbootapirestjava.mapper.ClaseMapper;
import springbootapirestjava.model.Clase;
import springbootapirestjava.repositories.ClaseRepository;

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
public class ClaseRestControllerMockMVCTest {
    private final ObjectMapper mapper = new ObjectMapper();
    @MockBean
    private final ClaseRepository claseRepository;
    @MockBean
    private final ClaseMapper claseMapper;
    private final Clase clase = Clase.builder()
            .id("ec272c62-9d31-11ec-b909-0242ac120002")
            .clasification(7.0)
            .build();
    private final ClaseDTO claseDTO = ClaseDTO.builder()
            .id(clase.getId())
            .clasification(clase.getClasification())
            .build();

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JacksonTester<ClaseDTO> jsonCreateServiceDTO;
    @Autowired
    private JacksonTester<ClaseDTO> jsonServiceDTO;

    @Autowired
    public ClaseRestControllerMockMVCTest(ClaseRepository claseRepository, ClaseMapper claseMapper) {
        this.claseRepository = claseRepository;
        this.claseMapper = claseMapper;
    }

    @Test
    @Order(1)
    void findAllTest() throws Exception {
        Mockito.when(claseRepository.findAll()).thenReturn(List.of(clase));
        Mockito.when(claseMapper.toDTO(List.of(clase))).thenReturn(List.of(claseDTO));

        mockMvc
                .perform(
                        get("/rest/clase/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOi" +
                                        "JjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NzY2MzgsImV4cCI6MT" +
                                        "Y0NjY2MzAzOCwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE1JTiJ9.HIY1f7O_OWY9SDfnJHkgN" +
                                        "GvqxqbWuJutdF_cnA7ulkwYz-LrDpAFrsFd9MFSQCL7Ms87cqALVqXsV4z0cphRYg"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(clase.getId())))
                .andExpect(jsonPath("$[0].clasification", is(clase.getClasification())))
                .andReturn();
        Mockito.verify(claseRepository, Mockito.times(1)).findAll();
        Mockito.verify(claseMapper, Mockito.times(1)).toDTO(List.of(clase));
    }

    @Test
    @Order(2)
    void findByIdTest() throws Exception {
        Mockito.when(claseRepository.findById(clase.getId()))
                .thenReturn(Optional.of(clase));
        Mockito.when(claseMapper.toDTO(clase)).thenReturn(claseDTO);
        mockMvc.perform(
                        get("/rest/clase/" + clase.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOi" +
                                        "JjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NzY2MzgsImV4cCI6MT" +
                                        "Y0NjY2MzAzOCwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE1JTiJ9.HIY1f7O_OWY9SDfnJHkgN" +
                                        "GvqxqbWuJutdF_cnA7ulkwYz-LrDpAFrsFd9MFSQCL7Ms87cqALVqXsV4z0cphRYg"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(clase.getId())))
                .andExpect(jsonPath("$.clasification", is(clase.getClasification())))
                .andReturn();
        Mockito.verify(claseRepository, Mockito.times(1)).findById(clase.getId());
        Mockito.verify(claseMapper, Mockito.times(1)).toDTO(clase);
    }

    @Test
    @Order(3)
    void findByExceptionTEst() throws Exception {
        Mockito.when(claseRepository.findById(clase.getId()))
                .thenReturn(Optional.empty());
        mockMvc.perform(
                        get("/rest/clase/" + clase.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOi" +
                                        "JjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NzY2MzgsImV4cCI6MT" +
                                        "Y0NjY2MzAzOCwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE1JTiJ9.HIY1f7O_OWY9SDfnJHkgN" +
                                        "GvqxqbWuJutdF_cnA7ulkwYz-LrDpAFrsFd9MFSQCL7Ms87cqALVqXsV4z0cphRYg"))
                .andExpect(status().isNotFound());
        Mockito.verify(claseRepository, Mockito.times(1)).findById(clase.getId());
    }

    @Test
    @Order(4)
    void updateTest() throws Exception {
        Mockito.when(claseRepository.findById(clase.getId())).thenReturn(Optional.of(clase));
        Mockito.when(claseRepository.save(clase)).thenReturn(clase);
        Mockito.when(claseMapper.toDTO(clase)).thenReturn(claseDTO);
        var ser =  jsonServiceDTO.write(claseDTO).getJson();

        mockMvc.perform(
                        put("/rest/clase/" + clase.getId())
                                .content(ser)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOi" +
                                        "JjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NzY2MzgsImV4cCI6MT" +
                                        "Y0NjY2MzAzOCwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE1JTiJ9.HIY1f7O_OWY9SDfnJHkgN" +
                                        "GvqxqbWuJutdF_cnA7ulkwYz-LrDpAFrsFd9MFSQCL7Ms87cqALVqXsV4z0cphRYg"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(clase.getId())))
                .andExpect(jsonPath("$.clasification", is(clase.getClasification())))
                .andReturn();
        Mockito.verify(claseRepository, Mockito.times(1)).findById(clase.getId());
        Mockito.verify(claseRepository, Mockito.times(1)).save(clase);
        Mockito.verify(claseMapper, Mockito.times(1)).toDTO(clase);
    }
}
