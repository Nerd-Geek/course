package springbootapirestjava.dto.pupil;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import springbootapirestjava.dto.tuition.TuitionDTO;
import springbootapirestjava.dto.clase.ClaseDTO;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PupilDTO {
    private String id = UUID.randomUUID().toString();
    private String name;
    private String email;
    private LocalDate createAt = LocalDate.now();
    private LocalDate updateAt;
    private String image;
    @JsonBackReference
    private ClaseDTO clase;
    @JsonBackReference
    private TuitionDTO tuition;
}
