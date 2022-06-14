package springbootapirestjava.dto.module;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import springbootapirestjava.dto.clase.ClaseDTO;
import springbootapirestjava.dto.course.CourseDTO;
import springbootapirestjava.model.Clase;
import springbootapirestjava.model.Course;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModuleDTO {
    private String id = UUID.randomUUID().toString();
    private String name;
    private String acronym;
    private Date createAt = Date.from(Instant.now());
    @JsonBackReference
    private CourseDTO course;
    @JsonBackReference
    private ClaseDTO clase;
}
