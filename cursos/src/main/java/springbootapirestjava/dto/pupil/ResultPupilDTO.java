package springbootapirestjava.dto.pupil;

import lombok.Getter;
import lombok.Setter;
import springbootapirestjava.config.APIConfig;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ResultPupilDTO {
    LocalDateTime consulta = LocalDateTime.now();
    String project = "SpringBootAPIRest";
    String version = APIConfig.API_VERSION;
    List<PupilDTO> data;

    public ResultPupilDTO(List<PupilDTO> data) {
        this.data = data;
    }
}
