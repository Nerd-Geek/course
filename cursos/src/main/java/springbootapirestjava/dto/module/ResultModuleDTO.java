package springbootapirestjava.dto.module;

import lombok.Getter;
import lombok.Setter;
import springbootapirestjava.config.APIConfig;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ResultModuleDTO {
    LocalDateTime consulta = LocalDateTime.now();
    String project = "SpringBootAPIRest";
    String version = APIConfig.API_VERSION;
    List<ModuleDTO> data;

    public ResultModuleDTO(List<ModuleDTO> data) {
        this.data = data;
    }
}
