package springbootapirestjava.dto.pupil;

import lombok.*;
import springbootapirestjava.config.APIConfig;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePupilDTO {
    private String id;
    private String name;
    private String image;
    private String password;
    private String password2;
    private String email;

    public CreatePupilDTO (String name, String image, String password, String password2, String email) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.image = image;
        this.password = password;
        this.password2 = password2;
        this.email = email;
    }
}
