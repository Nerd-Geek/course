package springbootapirestjava.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import springbootapirestjava.dto.pupil.PupilDTO;
import springbootapirestjava.model.Pupil;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PupilMapper {
    private final ModelMapper modelMapper;

    public PupilDTO toDTO(Pupil item) {
        return modelMapper.map(item, PupilDTO.class);
    }

    public List<PupilDTO> toDTO(List<Pupil> items) {
        return items.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Pupil toModel(PupilDTO item) {
        return modelMapper.map(item, Pupil.class);
    }

    public List<Pupil> toModel(List<PupilDTO> items) {
        return items.stream().map(this::toModel).collect(Collectors.toList());
    }
}
