package springbootapirestjava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import springbootapirestjava.model.Course;

import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, String> {
}
