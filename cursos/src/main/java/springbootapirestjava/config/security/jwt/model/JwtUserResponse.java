package springbootapirestjava.config.security.jwt.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import springbootapirestjava.dto.pupil.PupilDTO;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class JwtUserResponse extends PupilDTO {
    @NotNull(message = "El token no puede ser nulo")
    private String token;

    @Builder(builderMethodName = "jwtUserResponseBuilder")
    public JwtUserResponse(String id, String image,String name, Date createAt,
                           Date updateAt, String email, Set<String> userRoles, String token) {
        super(id, name, email,createAt, updateAt, image, userRoles);
        this.token = token;
    }
}
