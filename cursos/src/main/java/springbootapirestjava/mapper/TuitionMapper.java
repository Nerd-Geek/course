package springbootapirestjava.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import springbootapirestjava.dto.tuition.TuitionDTO;
import springbootapirestjava.model.Tuition;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TuitionMapper {
    private final ModelMapper modelMapper;

    public TuitionDTO toDTO(Tuition item) {
        return modelMapper.map(item, TuitionDTO.class);
    }

    public List<TuitionDTO> toDTO(List<Tuition> items) {
        return items.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Tuition toModel(TuitionDTO item) {
        return modelMapper.map(item, Tuition.class);
    }

    public List<Tuition> toModel(List<TuitionDTO> items) {
        return items.stream().map(this::toModel).collect(Collectors.toList());
    }
}
