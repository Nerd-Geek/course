package springbootapirestjava.controller.course;

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
import springbootapirestjava.mapper.CourseMapper;
import springbootapirestjava.model.Course;
import springbootapirestjava.model.NamberCourse;
import springbootapirestjava.repositories.CourseRepository;

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
public class CourseRestControllerMockMVCTest {
    private final ObjectMapper mapper = new ObjectMapper();
    @MockBean
    private final CourseRepository courseRepository;
    @MockBean
    private final CourseMapper courseMapper;
    private final Course course = Course.builder()
            .id("ec272c62-9d31-11ec-b909-0242ac120002")
            .namberCourse(NamberCourse.First)
            .acronym("DAM")
            .build();
    private final CourseDTO courseDTO = CourseDTO.builder()
            .id(course.getId())
            .namberCourse(course.getNamberCourse())
            .acronym(course.getAcronym())
            .build();

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JacksonTester<CourseDTO> jsonCreateCourseDTO;
    @Autowired
    private JacksonTester<CourseDTO> jsonCourseDTO;

    @Autowired
    public CourseRestControllerMockMVCTest(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    @Test
    @Order(1)
    void findAllTest() throws Exception {
        Mockito.when(courseRepository.findAll()).thenReturn(List.of(course));
        Mockito.when(courseMapper.toDTO(List.of(course))).thenReturn(List.of(courseDTO));

        mockMvc
                .perform(
                        get("/rest/course/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOi" +
                                        "JjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NzY2MzgsImV4cCI6MT" +
                                        "Y0NjY2MzAzOCwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE1JTiJ9.HIY1f7O_OWY9SDfnJHkgN" +
                                        "GvqxqbWuJutdF_cnA7ulkwYz-LrDpAFrsFd9MFSQCL7Ms87cqALVqXsV4z0cphRYg"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(course.getId())))
                .andExpect(jsonPath("$[0].acronym", is(course.getAcronym())))
                .andReturn();
        Mockito.verify(courseRepository, Mockito.times(1)).findAll();
        Mockito.verify(courseMapper, Mockito.times(1)).toDTO(List.of(course));
    }

    @Test
    @Order(2)
    void findByIdTest() throws Exception {
        Mockito.when(courseRepository.findById(course.getId()))
                .thenReturn(Optional.of(course));
        Mockito.when(courseMapper.toDTO(course)).thenReturn(courseDTO);
        mockMvc.perform(
                        get("/rest/course/" + course.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOi" +
                                        "JjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NzY2MzgsImV4cCI6MT" +
                                        "Y0NjY2MzAzOCwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE1JTiJ9.HIY1f7O_OWY9SDfnJHkgN" +
                                        "GvqxqbWuJutdF_cnA7ulkwYz-LrDpAFrsFd9MFSQCL7Ms87cqALVqXsV4z0cphRYg"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(course.getId())))
                .andExpect(jsonPath("$.acronym", is(course.getAcronym())))
                .andReturn();
        Mockito.verify(courseRepository, Mockito.times(1)).findById(course.getId());
        Mockito.verify(courseMapper, Mockito.times(1)).toDTO(course);
    }

    @Test
    @Order(3)
    void findByExceptionTEst() throws Exception {
        Mockito.when(courseRepository.findById(course.getId()))
                .thenReturn(Optional.empty());
        mockMvc.perform(
                        get("/rest/course/" + course.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOi" +
                                        "JjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NzY2MzgsImV4cCI6MT" +
                                        "Y0NjY2MzAzOCwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE1JTiJ9.HIY1f7O_OWY9SDfnJHkgN" +
                                        "GvqxqbWuJutdF_cnA7ulkwYz-LrDpAFrsFd9MFSQCL7Ms87cqALVqXsV4z0cphRYg"))
                .andExpect(status().isNotFound());
        Mockito.verify(courseRepository, Mockito.times(1)).findById(course.getId());
    }

    @Test
    @Order(4)
    void updateTest() throws Exception {
        Mockito.when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
        Mockito.when(courseRepository.save(course)).thenReturn(course);
        Mockito.when(courseMapper.toDTO(course)).thenReturn(courseDTO);
        var ser =  jsonCourseDTO.write(courseDTO).getJson();

        mockMvc.perform(
                        put("/rest/course/" + course.getId())
                                .content(ser)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOi" +
                                        "JjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NzY2MzgsImV4cCI6MT" +
                                        "Y0NjY2MzAzOCwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE1JTiJ9.HIY1f7O_OWY9SDfnJHkgN" +
                                        "GvqxqbWuJutdF_cnA7ulkwYz-LrDpAFrsFd9MFSQCL7Ms87cqALVqXsV4z0cphRYg"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(course.getId())))
                .andExpect(jsonPath("$.acronym", is(course.getAcronym())))
                .andReturn();
        Mockito.verify(courseRepository, Mockito.times(1)).findById(course.getId());
        Mockito.verify(courseRepository, Mockito.times(1)).save(course);
        Mockito.verify(courseMapper, Mockito.times(1)).toDTO(course);
    }
}
