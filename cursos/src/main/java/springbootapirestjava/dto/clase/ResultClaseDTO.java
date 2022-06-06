package springbootapirestjava.dto.clase;

import lombok.Getter;
import lombok.Setter;
import springbootapirestjava.config.APIConfig;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ResultClaseDTO {
    LocalDateTime consulta = LocalDateTime.now();
    String project = "SpringBootAPIRest";
    String version = APIConfig.API_VERSION;
    List<ClaseDTO> data;

    public ResultClaseDTO(List<ClaseDTO> data) {
        this.data = data;
    }
}
