package springbootapirestjava.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import springbootapirestjava.dto.clase.ClaseDTO;
import springbootapirestjava.model.Clase;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ClaseMapper {
    private final ModelMapper modelMapper;

    public ClaseDTO toDTO(Clase item) {
        return modelMapper.map(item, ClaseDTO.class);
    }

    public List<ClaseDTO> toDTO(List<Clase> items) {
        return items.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Clase toModel(ClaseDTO item) {
        return modelMapper.map(item, Clase.class);
    }

    public List<Clase> toModel(List<ClaseDTO> items) {
        return items.stream().map(this::toModel).collect(Collectors.toList());
    }
}
