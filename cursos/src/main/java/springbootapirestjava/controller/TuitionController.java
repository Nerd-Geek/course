package springbootapirestjava.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springbootapirestjava.config.APIConfig;
import springbootapirestjava.dto.tuition.ResultTuitionDTO;
import springbootapirestjava.dto.tuition.TuitionDTO;
import springbootapirestjava.mapper.TuitionMapper;
import springbootapirestjava.model.Tuition;
import springbootapirestjava.repositories.TuitionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(APIConfig.API_PATH + "/tuition")
public class TuitionController {

    private final TuitionRepository repository;
    private final TuitionMapper mapper;

    @GetMapping("/")
    public ResponseEntity<ResultTuitionDTO> getAll() {
        List<Tuition> tuitions = repository.findAll();
        ResultTuitionDTO result = new ResultTuitionDTO(mapper.toDTO(tuitions));
        return ResponseEntity.status(HttpStatus.OK)
                .body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultTuitionDTO> findById(@PathVariable String id) {
        Tuition tuition = repository.findById(id).orElse(null);
        ResultTuitionDTO result;
        if (tuition != null) {
            result = new ResultTuitionDTO(mapper.toDTO(List.of(tuition)));
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else {
            result = new ResultTuitionDTO(mapper.toDTO(new ArrayList<>()));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
    }

    @PostMapping("/")
    public ResponseEntity<TuitionDTO> postTuition(@RequestBody TuitionDTO tuitionDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toDTO(repository.save(mapper.toModel(tuitionDTO))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultTuitionDTO> putTuition(@PathVariable String id, @RequestBody TuitionDTO tuitionDTO) {
        Optional<Tuition> tuition = repository.findById(id);
        tuitionDTO.setId(id);
        if (tuition.isPresent())
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResultTuitionDTO(List.of(mapper.toDTO(repository.save(mapper.toModel(tuitionDTO))))));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResultTuitionDTO(new ArrayList<>()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResultTuitionDTO> delete(@PathVariable String id) {
        Optional<Tuition> tuition = repository.findById(id);
        if (tuition.isPresent()) {
            repository.delete(tuition.get());
            return ResponseEntity.ok(new ResultTuitionDTO(List.of(mapper.toDTO(tuition.get()))));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResultTuitionDTO(new ArrayList<>()));
    }
}
