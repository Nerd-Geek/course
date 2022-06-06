package springbootapirestjava.repositories.course;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import springbootapirestjava.model.Course;
import springbootapirestjava.model.NamberCourse;
import springbootapirestjava.repositories.CourseRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CourseRepositoryMockTest {
    private final Course course = Course.builder()
            .id("deb1c266-e4d2-11ec-8fea-0242ac120002")
            .namberCourse(NamberCourse.First)
            .acronym("DAM")
            .createdAt(Date.from(Instant.now()))
            .tuitions(new HashSet<>())
            .modules(new HashSet<>())
            .build();

    @MockBean
    private CourseRepository courseRepository;

    @Test
    @Order(1)
    void save() {
        Mockito.when(courseRepository.save(course)).thenReturn(course);
        Course cour = courseRepository.save(course);
        assertAll(
                () -> assertNotNull(cour),
                () -> assertEquals(course.getId(), cour.getId()),
                () -> assertEquals(course.getNamberCourse(), cour.getNamberCourse()),
                () -> assertEquals(course.getAcronym(), cour.getAcronym()),
                () -> assertEquals(course.getCreatedAt(), cour.getCreatedAt())
        );

        Mockito.verify(courseRepository, Mockito.times(1)).save(course);
    }

    @Test
    @Order(2)
    void findById() {
        Mockito.when(courseRepository.findById(course.getId()))
                .thenReturn(java.util.Optional.of(course));
        Course cour = courseRepository.findById(course.getId()).get();
        assertAll(
                () -> assertEquals(course, cour),
                () -> assertEquals(course.getId(), cour.getId()),
                () -> assertEquals(course.getNamberCourse(), cour.getNamberCourse()),
                () -> assertEquals(course.getAcronym(), cour.getAcronym()),
                () -> assertEquals(course.getCreatedAt(), cour.getCreatedAt())
        );

        Mockito.verify(courseRepository, Mockito.times(1)).findById(course.getId());
    }

    @Test
    @Order(3)
    void findAll() {
        Mockito.when(courseRepository.findAll()).thenReturn(List.of(course));
        List<Course> cour = courseRepository.findAll();
        assertAll(
                () -> assertEquals(List.of(course), cour),
                () -> assertEquals(course.getId(), cour.get(0).getId()),
                () -> assertEquals(course.getNamberCourse(), cour.get(0).getNamberCourse()),
                () -> assertEquals(course.getAcronym(), cour.get(0).getAcronym()),
                () -> assertEquals(course.getCreatedAt(), cour.get(0).getCreatedAt())
        );
        Mockito.verify(courseRepository, Mockito.times(1)).findAll();
    }

    @Test
    @Order(4)
    void update() {
        Mockito.when(courseRepository.save(course)).thenReturn(course);
        Course cour = courseRepository.save(course);
        assertAll(
                () -> assertEquals(course, cour),
                () -> assertEquals(course.getId(), cour.getId()),
                () -> assertEquals(course.getNamberCourse(), cour.getNamberCourse()),
                () -> assertEquals(course.getAcronym(), cour.getAcronym()),
                () -> assertEquals(course.getCreatedAt(), cour.getCreatedAt())
        );
        Mockito.verify(courseRepository, Mockito.times(1)).save(course);
    }
    @Test
    @Order(5)
    void delete() {
        Mockito.doNothing().when(courseRepository).delete(course);
        courseRepository.delete(course);
        Mockito.verify(courseRepository, Mockito.times(1)).delete(course);
    }
}
