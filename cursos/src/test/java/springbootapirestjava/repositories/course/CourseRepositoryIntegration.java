package springbootapirestjava.repositories.course;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import springbootapirestjava.model.Course;
import springbootapirestjava.model.NamberCourse;
import springbootapirestjava.repositories.CourseRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class CourseRepositoryIntegration {
    private final Course course = Course.builder()
            .id("deb1c266-e4d2-11ec-8fea-0242ac120002")
            .namberCourse(NamberCourse.First)
            .acronym("DAM")
            .createdAt(Date.from(Instant.now()))
            .tuitions(new HashSet<>())
            .modules(new HashSet<>())
            .build();

    @Autowired
    private CourseRepository courseRepository;

    @Test
    @Order(1)
    void save() {
        Course cour = courseRepository.save(course);

        assertAll(
                () -> assertNotNull(cour),
                () -> assertEquals(course.getId(), cour.getId()),
                () -> assertEquals(course.getNamberCourse(), cour.getNamberCourse()),
                () -> assertEquals(course.getAcronym(), cour.getAcronym()),
                () -> assertEquals(course.getCreatedAt(), cour.getCreatedAt())
        );
    }

    @Test
    @Order(2)
    void getAllCourse() {
        assertTrue(courseRepository.findAll().size() > 0);
    }

    @Test
    @Order(3)
    void getCourseId() {
        Course cours = courseRepository.save(course);
        Course cour = courseRepository.findById(cours.getId()).get();
        assertAll(
                () -> assertNotNull(cour),
                () -> assertEquals(course.getId(), cour.getId()),
                () -> assertEquals(course.getNamberCourse(), cour.getNamberCourse()),
                () -> assertEquals(course.getAcronym(), cour.getAcronym()),
                () -> assertEquals(course.getCreatedAt(), cour.getCreatedAt())
        );
    }

    @Test
    @Order(4)
    void updateCourse() {
        Course cour = courseRepository.save(course);
        cour = courseRepository.findById(cour.getId()).get();
        cour.setAcronym("DAW");

        Course cours = courseRepository.save(cour);
        assertAll(
                () -> assertNotNull(cours),
                () -> assertEquals(course.getId(), cours.getId()),
                () -> assertEquals(course.getNamberCourse(), cours.getNamberCourse()),
                () -> assertEquals("DAW", cours.getAcronym()),
                () -> assertEquals(course.getCreatedAt(), cours.getCreatedAt())
        );
    }

    @Test
    @Order(5)
    void deleteCourse() {
        Course cour = courseRepository.save(course);
        cour = courseRepository.findById(cour.getId()).get();

        courseRepository.delete(cour);

        assertNull(courseRepository.findById(cour.getId()).orElse(null));
    }
}
