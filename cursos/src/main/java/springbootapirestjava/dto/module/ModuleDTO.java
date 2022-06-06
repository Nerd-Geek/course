package springbootapirestjava.dto.module;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import springbootapirestjava.dto.clase.ClaseDTO;
import springbootapirestjava.dto.course.CourseDTO;

import java.time.LocalDate;
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
    private LocalDate createAt = LocalDate.now();
    @JsonBackReference
    private CourseDTO course;
    @JsonBackReference
    private ClaseDTO clase;
}
