package springbootapirestjava.dto.pupil;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import springbootapirestjava.dto.tuition.TuitionDTO;
import springbootapirestjava.dto.clase.ClaseDTO;

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
public class PupilDTO {
    private String id = UUID.randomUUID().toString();
    private String name;
    private String email;
    private Date createAt = Date.from(Instant.now());
    private Date updateAt;
    private String image;
    private Set<String> roles;
}
