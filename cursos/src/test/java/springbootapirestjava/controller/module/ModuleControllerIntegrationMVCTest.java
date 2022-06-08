package springbootapirestjava.controller.module;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import springbootapirestjava.dto.module.ModuleDTO;
import springbootapirestjava.model.Modulo;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ModuleControllerIntegrationMVCTest {
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    MockMvc mockMvc;

    private final Modulo modulo = Modulo.builder()
            .id("4203de85-4295-4ff4-b776-033faec08005")
            .name("Acceso a datos")
            .acronym("AD")
            .createdAt(Date.from(Instant.now()))
            .build();

    @Autowired
    private JacksonTester<ModuleDTO> jsonCreateModuleDTO;
    @Autowired
    private JacksonTester<ModuleDTO> jsonModuleDTO;

    @Test
    @Order(1)
    void findAllTest() throws Exception {

        MockHttpServletResponse response = mockMvc.perform(
                        get("/rest/modulo/")
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOi" +
                                        "JjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NzY2MzgsImV4cCI6MT" +
                                        "Y0NjY2MzAzOCwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE1JTiJ9.HIY1f7O_OWY9SDfnJHkgN" +
                                        "GvqxqbWuJutdF_cnA7ulkwYz-LrDpAFrsFd9MFSQCL7Ms87cqALVqXsV4z0cphRYg"))
                .andReturn().getResponse();

        ObjectMapper mapper = new ObjectMapper();
        List<ModuleDTO> myObjects = Arrays.asList(mapper.readValue(response.getContentAsString(), ModuleDTO[].class));

        assertAll(
                () -> assertEquals(myObjects.get(0).getId(), modulo.getId()),
                () -> assertEquals(myObjects.get(0).getName(), modulo.getName()),
                () -> assertEquals(myObjects.get(0).getAcronym(), modulo.getAcronym())
        );
    }

    @Test
    @Order(2)
    void findByIdTest() throws Exception {

        var response = mockMvc.perform(
                        get("/rest/modulo/" + modulo.getId())
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOi" +
                                        "JjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NzY2MzgsImV4cCI6MT" +
                                        "Y0NjY2MzAzOCwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE1JTiJ9.HIY1f7O_OWY9SDfnJHkgN" +
                                        "GvqxqbWuJutdF_cnA7ulkwYz-LrDpAFrsFd9MFSQCL7Ms87cqALVqXsV4z0cphRYg"))
                .andReturn().getResponse();

        var res = jsonModuleDTO.parseObject(response.getContentAsString());

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
                () -> assertEquals(res.getId(), modulo.getId()),
                () -> assertEquals(res.getName(), modulo.getName()),
                () -> assertEquals(res.getAcronym(), modulo.getAcronym())
        );
    }

    @Test
    @Order(3)
    void saveTest() throws Exception {
        ModuleDTO moduleDTO = ModuleDTO.builder()
                .id(modulo.getId())
                .name(modulo.getName())
                .acronym(modulo.getAcronym())
                .build();

        var response = mockMvc.perform(MockMvcRequestBuilders.post("/rest/modulo/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCreateModuleDTO.write(moduleDTO).getJson())
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOi" +
                        "JjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NzY2MzgsImV4cCI6MT" +
                        "Y0NjY2MzAzOCwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE1JTiJ9.HIY1f7O_OWY9SDfnJHkgN" +
                        "GvqxqbWuJutdF_cnA7ulkwYz-LrDpAFrsFd9MFSQCL7Ms87cqALVqXsV4z0cphRYg")).andReturn().getResponse();

        var res = jsonModuleDTO.parseObject(response.getContentAsString());

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
                () -> assertEquals(res.getName(), modulo.getName()),
                () -> assertEquals(res.getName(), moduleDTO.getName()),
                () -> assertEquals(res.getAcronym(), moduleDTO.getAcronym())
        );
    }

    @Test
    @Order(4)
    void updateTest() throws Exception {

        ModuleDTO moduleDTO = ModuleDTO.builder()
                .id(modulo.getId())
                .name(modulo.getName())
                .acronym(modulo.getAcronym())
                .build();

        var response = mockMvc.perform(MockMvcRequestBuilders.put("/rest/modulo/" + modulo.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonModuleDTO.write(moduleDTO).getJson())
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOi" +
                        "JjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NzY2MzgsImV4cCI6MT" +
                        "Y0NjY2MzAzOCwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE1JTiJ9.HIY1f7O_OWY9SDfnJHkgN" +
                        "GvqxqbWuJutdF_cnA7ulkwYz-LrDpAFrsFd9MFSQCL7Ms87cqALVqXsV4z0cphRYg")).andReturn().getResponse();

        var res = jsonModuleDTO.parseObject(response.getContentAsString());

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
                () -> assertEquals(res.getId(), modulo.getId()),
                () -> assertEquals(res.getName(), moduleDTO.getName()),
                () -> assertEquals(res.getAcronym(), moduleDTO.getAcronym())
        );
    }

    @Test
    @Order(5)
    void findAllAlternativeTest() throws Exception {
        mockMvc.perform(get("/rest/modulo/")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOi" +
                                "JjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NzY2MzgsImV4cCI6MT" +
                                "Y0NjY2MzAzOCwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE1JTiJ9.HIY1f7O_OWY9SDfnJHkgN" +
                                "GvqxqbWuJutdF_cnA7ulkwYz-LrDpAFrsFd9MFSQCL7Ms87cqALVqXsV4z0cphRYg"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(modulo.getId())))
                .andExpect(jsonPath("$[0].name", is(modulo.getName())))
                .andExpect(jsonPath("$[0].acronym", is(modulo.getAcronym())))
                .andReturn();
    }

    @Test
    @Order(6)
    void findByIdlternativeTest() throws Exception {
        mockMvc.perform(get("/rest/modulo/" + modulo.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOi" +
                                "JjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NzY2MzgsImV4cCI6MT" +
                                "Y0NjY2MzAzOCwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE1JTiJ9.HIY1f7O_OWY9SDfnJHkgN" +
                                "GvqxqbWuJutdF_cnA7ulkwYz-LrDpAFrsFd9MFSQCL7Ms87cqALVqXsV4z0cphRYg"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(modulo.getId())))
                .andExpect(jsonPath("$.name", is(modulo.getName())))
                .andExpect(jsonPath("$.acronym", is(modulo.getAcronym())))
                .andReturn();
    }

    @Test
    @Order(7)
    void postAlternativeTest() throws Exception {
        ModuleDTO moduleDTO = ModuleDTO.builder()
                .id(modulo.getId())
                .name(modulo.getName())
                .acronym(modulo.getAcronym())
                .build();


        var json = jsonModuleDTO.write(moduleDTO).getJson();

        mockMvc.perform(post("/rest/modulo/")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOi" +
                                "JjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NzY2MzgsImV4cCI6MT" +
                                "Y0NjY2MzAzOCwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE1JTiJ9.HIY1f7O_OWY9SDfnJHkgN" +
                                "GvqxqbWuJutdF_cnA7ulkwYz-LrDpAFrsFd9MFSQCL7Ms87cqALVqXsV4z0cphRYg"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(modulo.getId())))
                .andExpect(jsonPath("$.name", is(modulo.getName())))
                .andExpect(jsonPath("$.acronym", is(modulo.getAcronym())))
                .andReturn();
    }

    @Test
    @Order(8)
    void updateAlternativeTest() throws Exception {
        ModuleDTO moduleDTO = ModuleDTO.builder()
                .id(modulo.getId())
                .name(modulo.getName())
                .acronym(modulo.getAcronym())
                .build();

        var json = jsonModuleDTO.write(moduleDTO).getJson();

        mockMvc.perform(put("/rest/modulo/" + modulo.getId())
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOi" +
                                "JjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NzY2MzgsImV4cCI6MT" +
                                "Y0NjY2MzAzOCwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE1JTiJ9.HIY1f7O_OWY9SDfnJHkgN" +
                                "GvqxqbWuJutdF_cnA7ulkwYz-LrDpAFrsFd9MFSQCL7Ms87cqALVqXsV4z0cphRYg"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(modulo.getId())))
                .andExpect(jsonPath("$.name", is(modulo.getName())))
                .andExpect(jsonPath("$.acronym", is(modulo.getAcronym())))
                .andReturn();
    }
}
