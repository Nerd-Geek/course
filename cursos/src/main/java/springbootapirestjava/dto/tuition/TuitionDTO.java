package springbootapirestjava.dto.tuition;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import springbootapirestjava.dto.course.CourseDTO;
import springbootapirestjava.dto.pupil.PupilDTO;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TuitionDTO {
    private String id = UUID.randomUUID().toString();
    @JsonBackReference
    private CourseDTO course;
    @JsonManagedReference
    private Set<PupilDTO> pupils;
}
