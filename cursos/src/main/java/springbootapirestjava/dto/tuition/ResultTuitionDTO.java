package springbootapirestjava.dto.tuition;

import lombok.Getter;
import lombok.Setter;
import springbootapirestjava.config.APIConfig;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ResultTuitionDTO {
    LocalDateTime consulta = LocalDateTime.now();
    String project = "SpringBootAPIRest";
    String version = APIConfig.API_VERSION;
    List<TuitionDTO> data;

    public ResultTuitionDTO(List<TuitionDTO> data) {
        this.data = data;
    }
}
