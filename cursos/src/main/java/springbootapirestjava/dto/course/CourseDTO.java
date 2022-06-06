package springbootapirestjava.dto.course;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import springbootapirestjava.dto.module.ModuleDTO;
import springbootapirestjava.dto.tuition.TuitionDTO;
import springbootapirestjava.model.NamberCourse;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO {
    private String id = UUID.randomUUID().toString();
    private NamberCourse namberCourse;
    private String acronym;
    private Date createAt = Date.from(Instant.now());
}
