package springbootapirestjava.dto.module;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import springbootapirestjava.dto.clase.ClaseDTO;
import springbootapirestjava.dto.course.CourseDTO;

import java.time.Instant;
import java.time.LocalDate;
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
}
