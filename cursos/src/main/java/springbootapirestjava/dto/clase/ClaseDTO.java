package springbootapirestjava.dto.clase;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import springbootapirestjava.dto.module.ModuleDTO;
import springbootapirestjava.dto.pupil.PupilDTO;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClaseDTO {
    private String id = UUID.randomUUID().toString();
    private Double clasification;
    @JsonManagedReference
    private Set<ModuleDTO> modules;
    @JsonManagedReference
    private Set<PupilDTO> pupils;
}
