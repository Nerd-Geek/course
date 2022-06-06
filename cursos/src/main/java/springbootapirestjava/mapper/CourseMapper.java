package springbootapirestjava.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import springbootapirestjava.dto.course.CourseDTO;
import springbootapirestjava.model.Course;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CourseMapper {
    private final ModelMapper modelMapper;

    public CourseDTO toDTO(Course item) {
        return modelMapper.map(item, CourseDTO.class);
    }

    public List<CourseDTO> toDTO(List<Course> items) {
        return items.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Course toModel(CourseDTO item) {
        return modelMapper.map(item, Course.class);
    }

    public List<Course> toModel(List<CourseDTO> items) {
        return items.stream().map(this::toModel).collect(Collectors.toList());
    }
}
