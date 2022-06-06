package springbootapirestjava.dto.course;

import lombok.Getter;
import lombok.Setter;
import springbootapirestjava.config.APIConfig;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ResultCourseDTO {
    LocalDateTime consulta = LocalDateTime.now();
    String project = "SpringBootAPIRest";
    String version = APIConfig.API_VERSION;
    List<CourseDTO> data;

    public ResultCourseDTO(List<CourseDTO> data) {
        this.data = data;
    }
}
