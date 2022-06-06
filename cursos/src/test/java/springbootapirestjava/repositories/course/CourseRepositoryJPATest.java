package springbootapirestjava.repositories.course;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.filter.TypeExcludeFilters;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import springbootapirestjava.model.Course;
import springbootapirestjava.model.NamberCourse;
import springbootapirestjava.repositories.CourseRepository;

import javax.transaction.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
@TypeExcludeFilters(value= DataJpaTypeExcludeFilter.class)
@AutoConfigureCache
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@ImportAutoConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CourseRepositoryJPATest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CourseRepository courseRepository;

    private final Course course = Course.builder()
            .id("deb1c266-e4d2-11ec-8fea-0242ac120002")
            .namberCourse(NamberCourse.First)
            .acronym("DAM")
            .createdAt(Date.from(Instant.now()))
            .tuitions(new HashSet<>())
            .modules(new HashSet<>())
            .build();

    @Test
    @Order(1)
    void getAllTest() {
        entityManager.persist(course);
        entityManager.flush();

        assertTrue(courseRepository.findAll().size() > 0);
    }

    @Test
    @Order(2)
    void getByIdTest() {
        entityManager.persist(course);
        entityManager.flush();

        Course courseFound = courseRepository.findById(course.getId()).get();
        assertAll(
                () -> assertNotNull(courseFound),
                () -> assertEquals(course.getId(), courseFound.getId()),
                () -> assertEquals(course.getNamberCourse(), courseFound.getNamberCourse()),
                () -> assertEquals(course.getAcronym(), courseFound.getAcronym()),
                () -> assertEquals(course.getCreatedAt(), courseFound.getCreatedAt())
        );
    }

    @Test
    @Order(3)
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
    @Order(4)
    void update() {
        entityManager.persist(course);
        entityManager.flush();
        Course courseFound = courseRepository.findById(course.getId()).get();
        courseFound.setAcronym("DAW");

        Course updated = courseRepository.save(courseFound);
        assertAll(
                () -> assertNotNull(updated),
                () -> assertEquals(course.getId(), updated.getId()),
                () -> assertEquals(course.getNamberCourse(), updated.getNamberCourse()),
                () -> assertEquals(course.getAcronym(), updated.getAcronym()),
                () -> assertEquals(course.getCreatedAt(), updated.getCreatedAt())
        );
    }

    @Test
    @Order(5)
    void delete() {
        entityManager.persist(course);
        entityManager.flush();
        courseRepository.delete(course);
        Course cour = courseRepository.findById(course.getId()).orElse(null);
        assertNull(cour);
    }
}
