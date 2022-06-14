package springbootapirestjava.dto.pupil;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import springbootapirestjava.dto.clase.ClaseDTO;
import springbootapirestjava.dto.tuition.TuitionDTO;
import springbootapirestjava.model.Clase;
import springbootapirestjava.model.Tuition;

import java.time.Instant;
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
    @JsonBackReference
    private ClaseDTO clase;
    @JsonBackReference
    private TuitionDTO tuition;

    public PupilDTO(String id, String name, String email, Date createAt, Date updateAt, String image, Set<String> userRoles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.image = image;
        this.roles = userRoles;
    }
}
