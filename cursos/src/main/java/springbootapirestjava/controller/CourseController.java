package springbootapirestjava.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springbootapirestjava.config.APIConfig;
import springbootapirestjava.dto.clase.ClaseDTO;
import springbootapirestjava.dto.course.CourseDTO;
import springbootapirestjava.dto.course.ResultCourseDTO;
import springbootapirestjava.exceptions.GeneralBadRequestException;
import springbootapirestjava.exceptions.course.CourseNotFoundException;
import springbootapirestjava.exceptions.course.CourseNotFountException;
import springbootapirestjava.mapper.CourseMapper;
import springbootapirestjava.model.Course;
import springbootapirestjava.repositories.CourseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(APIConfig.API_PATH + "/course")
public class CourseController {

    private final CourseRepository repository;
    private final CourseMapper mapper;

    @GetMapping("/")
    public ResponseEntity<List<CourseDTO>> getAll(@RequestParam(name = "limit") Optional<String> limit) {
        List<Course> courses = null;
        try {
            courses = repository.findAll();

            if (limit.isPresent() && !courses.isEmpty() && courses.size() > Integer.parseInt(limit.get())) {
                return ResponseEntity.ok(mapper.toDTO(courses.subList(0, Integer.parseInt(limit.get()))));
            } else {
                if (!courses.isEmpty()) {
                    return ResponseEntity.ok(mapper.toDTO(courses));
                } else {
                    throw new CourseNotFoundException();
                }
            }
        } catch (Exception e) {
            throw new GeneralBadRequestException("Selección de Datos", "Parámetros de consulta incorrectos");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> findById(@PathVariable String id) {
        Course course = repository.findById(id).orElse(null);
        if (course == null) {
            throw new CourseNotFountException(id);
        } else {
            return ResponseEntity.ok(mapper.toDTO(course));
        }
    }

    @PostMapping("/")
    public ResponseEntity<CourseDTO> postCourse(@RequestBody CourseDTO courseDTO) {
        Course course = mapper.toModel(courseDTO);
        Course courseInsert = repository.save(course);
        return ResponseEntity.ok(mapper.toDTO(courseInsert));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> putCourse(@PathVariable String id, @RequestBody CourseDTO courseDTO) {
        try {
            Course courseUpdated = repository.findById(id).orElse(null);
            if (courseUpdated == null) {
                throw new CourseNotFountException(id);
            } else {
                courseUpdated.setNamberCourse(courseDTO.getNamberCourse());
                courseUpdated.setAcronym(courseDTO.getAcronym());
                courseUpdated.setCreatedAt(courseDTO.getCreateAt());

                courseUpdated = repository.save(courseUpdated);

                return ResponseEntity.ok(mapper.toDTO(courseUpdated));
            }
        } catch (Exception e) {
            throw new GeneralBadRequestException("Actualizar", "Error al actualizar el service. Campos incorrectos.");
        }
    }
}
