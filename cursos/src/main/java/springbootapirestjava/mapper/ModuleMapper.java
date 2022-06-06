package springbootapirestjava.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import springbootapirestjava.dto.module.ModuleDTO;
import springbootapirestjava.model.Modulo;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ModuleMapper {
    private final ModelMapper modelMapper;

    public ModuleDTO toDTO(Modulo item) {
        return modelMapper.map(item, ModuleDTO.class);
    }

    public List<ModuleDTO> toDTO(List<Modulo> items) {
        return items.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Modulo toModel(ModuleDTO item) {
        return modelMapper.map(item, Modulo.class);
    }

    public List<Modulo> toModel(List<ModuleDTO> items) {
        return items.stream().map(this::toModel).collect(Collectors.toList());
    }
}
