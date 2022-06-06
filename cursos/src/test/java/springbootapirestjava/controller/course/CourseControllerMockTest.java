package springbootapirestjava.controller.course;

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
import springbootapirestjava.controller.CourseController;
import springbootapirestjava.dto.course.CourseDTO;
import springbootapirestjava.exceptions.course.CourseNotFountException;
import springbootapirestjava.mapper.CourseMapper;
import springbootapirestjava.model.Course;
import springbootapirestjava.model.NamberCourse;
import springbootapirestjava.repositories.CourseRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CourseControllerMockTest {
    @MockBean
    private final CourseRepository courseRepository;
    @MockBean
    private final CourseMapper courseMapper;
    private final Course course = Course.builder()
            .id("a4594ad4-e4ba-11ec-8fea-0242ac120002")
            .namberCourse(NamberCourse.First)
            .acronym("DAM")
            .createdAt(Date.from(Instant.now()))
            .build();
    @InjectMocks
    private CourseController courseController;
    @Autowired
    public CourseControllerMockTest(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    @Test
    @Order(1)
    void getAllTestMock() {
        CourseDTO courseDTO = CourseDTO.builder()
                .id(course.getId())
                .namberCourse(course.getNamberCourse())
                .acronym(course.getAcronym())
                .createAt(course.getCreatedAt())
                .build();

        Mockito.when(courseRepository.findAll())
                .thenReturn(List.of(course));

        Mockito.when(courseMapper.toDTO(List.of(course))).thenReturn(List.of(courseDTO));

        var response = courseController.getAll(
                java.util.Optional.empty()
        );
        var res = response.getBody();

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.get(0).getId(), course.getId()),
                () -> assertEquals(res.get(0).getNamberCourse(), course.getNamberCourse()),
                () -> assertEquals(res.get(0).getAcronym(), course.getAcronym()),
                () -> assertEquals(res.get(0).getCreateAt(), course.getCreatedAt())
        );
    }

    @Test
    @Order(2)
    void getByIdTestMock() {
        CourseDTO courseDTO = CourseDTO.builder()
                .id(course.getId())
                .namberCourse(course.getNamberCourse())
                .acronym(course.getAcronym())
                .createAt(course.getCreatedAt())
                .build();

        Mockito.when(courseRepository.findById(course.getId()))
                .thenReturn(Optional.of(course));

        Mockito.when(courseMapper.toDTO(course)).thenReturn(courseDTO);

        var response = courseController.findById(course.getId());
        var res = response.getBody();

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.getId(), course.getId()),
                () -> assertEquals(res.getNamberCourse(), course.getNamberCourse()),
                () -> assertEquals(res.getAcronym(), course.getAcronym()),
                () -> assertEquals(res.getCreateAt(), course.getCreatedAt())
        );

        Mockito.verify(courseRepository, Mockito.times(1)).findById(course.getId());
        Mockito.verify(courseMapper, Mockito.times(1)).toDTO(course);
    }

    @Test
    @Order(3)
    void findByIdException() {
        Mockito.when(courseRepository.findById(course.getId())).thenReturn(Optional.empty());

        Exception ex = assertThrows(CourseNotFountException.class, () -> {
            courseController.findById(course.getId());
        });

        assertTrue(ex.getMessage().contains("service"));

        Mockito.verify(courseRepository, Mockito.times(1))
                .findById(course.getId());
    }

    @Test
    @Order(4)
    void saveTestMock() {
        CourseDTO courseCreateDTO = CourseDTO.builder()
                .namberCourse(course.getNamberCourse())
                .acronym(course.getAcronym())
                .createAt(course.getCreatedAt())
                .build();

        CourseDTO courseDTO = CourseDTO.builder()
                .namberCourse(course.getNamberCourse())
                .acronym(course.getAcronym())
                .createAt(course.getCreatedAt())
                .build();

        Mockito.when(courseRepository.save(course))
                .thenReturn(course);

        Mockito.when(courseMapper.toModel(courseCreateDTO))
                .thenReturn(course);

        Mockito.when(courseMapper.toDTO(course)).thenReturn(courseDTO);

        var response = courseController.postCourse(courseCreateDTO);
        var res = response.getBody();

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.getNamberCourse(), course.getNamberCourse()),
                () -> assertEquals(res.getAcronym(), course.getAcronym()),
                () -> assertEquals(res.getCreateAt(), course.getCreatedAt())
        );

        Mockito.verify(courseRepository, Mockito.times(1))
                .save(course);
        Mockito.verify(courseMapper, Mockito.times(1))
                .toModel(courseCreateDTO);
        Mockito.verify(courseMapper, Mockito.times(1))
                .toDTO(course);
    }

    @Test
    @Order(5)
    void updateTestMock() {
        CourseDTO courseDTO = CourseDTO.builder()
                .id(course.getId())
                .namberCourse(course.getNamberCourse())
                .acronym(course.getAcronym())
                .build();

        Mockito.when(courseRepository.findById(course.getId()))
                .thenReturn(java.util.Optional.of(course));

        Mockito.when(courseRepository.save(course))
                .thenReturn(course);

        Mockito.when(courseMapper.toDTO(course)).thenReturn(courseDTO);

        var response = courseController.putCourse(course.getId(), courseDTO);
        var res = response.getBody();

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.getId(), course.getId()),
                () -> assertEquals(res.getNamberCourse(), course.getNamberCourse()),
                () -> assertEquals(res.getAcronym(), course.getAcronym())
        );

        Mockito.verify(courseRepository, Mockito.times(1))
                .findById(course.getId());
        Mockito.verify(courseRepository, Mockito.times(1))
                .save(course);
        Mockito.verify(courseMapper, Mockito.times(1))
                .toDTO(course);
    }

}
