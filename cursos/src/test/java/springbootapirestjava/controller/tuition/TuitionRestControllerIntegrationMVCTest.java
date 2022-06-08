package springbootapirestjava.controller.tuition;

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
import springbootapirestjava.dto.tuition.TuitionDTO;
import springbootapirestjava.model.Tuition;

import java.util.Arrays;
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
public class TuitionRestControllerIntegrationMVCTest {
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    MockMvc mockMvc;

    Tuition tuition = Tuition.builder()
            .id("47800823-edb7-4cc5-85ce-b578b2f6c47d ")
            .build();
    @Autowired
    private JacksonTester<TuitionDTO> jsonCreateTuitionDTO;
    @Autowired
    private JacksonTester<TuitionDTO> jsonTuitionDTO;

    @Test
    @Order(1)
    void findAllTest() throws Exception {

        MockHttpServletResponse response = mockMvc.perform(
                        get("/rest/tuition/")
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOi" +
                                        "JjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NzY2MzgsImV4cCI6MT" +
                                        "Y0NjY2MzAzOCwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE1JTiJ9.HIY1f7O_OWY9SDfnJHkgN" +
                                        "GvqxqbWuJutdF_cnA7ulkwYz-LrDpAFrsFd9MFSQCL7Ms87cqALVqXsV4z0cphRYg"))
                .andReturn().getResponse();

        ObjectMapper mapper = new ObjectMapper();
        List<TuitionDTO> myObjects = Arrays.asList(mapper.readValue(response.getContentAsString(), TuitionDTO[].class));

        assertAll(
                () -> assertEquals(myObjects.get(0).getId(), tuition.getId())
        );
    }

    @Test
    @Order(2)
    void findByIdTest() throws Exception {

        var response = mockMvc.perform(
                        get("/rest/tuition/" + tuition.getId())
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOi" +
                                        "JjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NzY2MzgsImV4cCI6MT" +
                                        "Y0NjY2MzAzOCwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE1JTiJ9.HIY1f7O_OWY9SDfnJHkgN" +
                                        "GvqxqbWuJutdF_cnA7ulkwYz-LrDpAFrsFd9MFSQCL7Ms87cqALVqXsV4z0cphRYg"))
                .andReturn().getResponse();

        var res = jsonTuitionDTO.parseObject(response.getContentAsString());

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
                () -> assertEquals(res.getId(), tuition.getId())
        );
    }

    @Test
    @Order(3)
    void saveTest() throws Exception {
        TuitionDTO tuitionDTO = TuitionDTO.builder()
                .id(tuition.getId())
                .build();

        var response = mockMvc.perform(MockMvcRequestBuilders.post("/rest/tuition/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCreateTuitionDTO.write(tuitionDTO).getJson())
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOi" +
                        "JjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NzY2MzgsImV4cCI6MT" +
                        "Y0NjY2MzAzOCwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE1JTiJ9.HIY1f7O_OWY9SDfnJHkgN" +
                        "GvqxqbWuJutdF_cnA7ulkwYz-LrDpAFrsFd9MFSQCL7Ms87cqALVqXsV4z0cphRYg")).andReturn().getResponse();

        var res = jsonTuitionDTO.parseObject(response.getContentAsString());

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
                () -> assertEquals(res.getId(), tuition.getId())
        );
    }

    @Test
    @Order(4)
    void updateTest() throws Exception {
        TuitionDTO tuitionDTO = TuitionDTO.builder()
                .id(tuition.getId())
                .build();

        var response = mockMvc.perform(MockMvcRequestBuilders.put("/rest/tuition/" + tuition.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonTuitionDTO.write(tuitionDTO).getJson())
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOi" +
                        "JjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NzY2MzgsImV4cCI6MT" +
                        "Y0NjY2MzAzOCwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE1JTiJ9.HIY1f7O_OWY9SDfnJHkgN" +
                        "GvqxqbWuJutdF_cnA7ulkwYz-LrDpAFrsFd9MFSQCL7Ms87cqALVqXsV4z0cphRYg")).andReturn().getResponse();

        var res = jsonTuitionDTO.parseObject(response.getContentAsString());

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
                () -> assertEquals(res.getId(), tuition.getId())
        );
    }

    @Test
    @Order(5)
    void deleteTest() throws Exception {

        var response = mockMvc.perform(MockMvcRequestBuilders.delete("/rest/tuition/" + tuition.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOi" +
                        "JjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NzY2MzgsImV4cCI6MT" +
                        "Y0NjY2MzAzOCwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE1JTiJ9.HIY1f7O_OWY9SDfnJHkgN" +
                        "GvqxqbWuJutdF_cnA7ulkwYz-LrDpAFrsFd9MFSQCL7Ms87cqALVqXsV4z0cphRYg")).andReturn().getResponse();

        var res = jsonTuitionDTO.parseObject(response.getContentAsString());

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
                () -> assertEquals(res.getId(), tuition.getId())
        );
    }

    @Test
    @Order(6)
    void findAllAlternativeTest() throws Exception {
        mockMvc.perform(get("/rest/tuition/")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOi" +
                                "JjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NzY2MzgsImV4cCI6MT" +
                                "Y0NjY2MzAzOCwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE1JTiJ9.HIY1f7O_OWY9SDfnJHkgN" +
                                "GvqxqbWuJutdF_cnA7ulkwYz-LrDpAFrsFd9MFSQCL7Ms87cqALVqXsV4z0cphRYg"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(tuition.getId())))
                .andReturn();
    }

    @Test
    @Order(7)
    void findByIdlternativeTest() throws Exception {
        mockMvc.perform(get("/rest/tuition/" + tuition.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOi" +
                                "JjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NzY2MzgsImV4cCI6MT" +
                                "Y0NjY2MzAzOCwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE1JTiJ9.HIY1f7O_OWY9SDfnJHkgN" +
                                "GvqxqbWuJutdF_cnA7ulkwYz-LrDpAFrsFd9MFSQCL7Ms87cqALVqXsV4z0cphRYg"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(tuition.getId())))
                .andReturn();
    }

    @Test
    @Order(8)
    void postAlternativeTest() throws Exception {
        TuitionDTO tuitionDTO = TuitionDTO.builder()
                .id(tuition.getId())
                .build();

        var json = jsonTuitionDTO.write(tuitionDTO).getJson();

        mockMvc.perform(post("/rest/tuition/")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOi" +
                                "JjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NzY2MzgsImV4cCI6MT" +
                                "Y0NjY2MzAzOCwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE1JTiJ9.HIY1f7O_OWY9SDfnJHkgN" +
                                "GvqxqbWuJutdF_cnA7ulkwYz-LrDpAFrsFd9MFSQCL7Ms87cqALVqXsV4z0cphRYg"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(tuition.getId())))
                .andReturn();
    }

    @Test
    @Order(9)
    void updateAlternativeTest() throws Exception {
        TuitionDTO tuitionDTO = TuitionDTO.builder()
                .id(tuition.getId())
                .build();

        var json = jsonTuitionDTO.write(tuitionDTO).getJson();

        mockMvc.perform(put("/rest/tuition/" + tuition.getId())
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOi" +
                                "JjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NzY2MzgsImV4cCI6MT" +
                                "Y0NjY2MzAzOCwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE1JTiJ9.HIY1f7O_OWY9SDfnJHkgN" +
                                "GvqxqbWuJutdF_cnA7ulkwYz-LrDpAFrsFd9MFSQCL7Ms87cqALVqXsV4z0cphRYg"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(tuition.getId())))
                .andReturn();
    }

    @Test
    @Order(10)
    void deleteAlternativeTest() throws Exception {
        mockMvc.perform(delete("/rest/tuition/" + tuition.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOi" +
                                "JjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NzY2MzgsImV4cCI6MT" +
                                "Y0NjY2MzAzOCwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE1JTiJ9.HIY1f7O_OWY9SDfnJHkgN" +
                                "GvqxqbWuJutdF_cnA7ulkwYz-LrDpAFrsFd9MFSQCL7Ms87cqALVqXsV4z0cphRYg"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(tuition.getId())))
                .andReturn();
    }
}
