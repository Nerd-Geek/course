package springbootapirestjava.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springbootapirestjava.config.APIConfig;
import springbootapirestjava.dto.tuition.ResultTuitionDTO;
import springbootapirestjava.dto.tuition.TuitionDTO;
import springbootapirestjava.exceptions.GeneralBadRequestException;
import springbootapirestjava.exceptions.tuition.TuitionNotFoundException;
import springbootapirestjava.exceptions.tuition.TuitionNotFountException;
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
    public ResponseEntity<List<TuitionDTO>> getAll(@RequestParam(name = "limit") Optional<String> limit) {
        List<Tuition> services = null;
        try {
            services = repository.findAll();

            if (limit.isPresent() && !services.isEmpty() && services.size() > Integer.parseInt(limit.get())) {
                return ResponseEntity.ok(mapper.toDTO(services.subList(0, Integer.parseInt(limit.get()))));
            } else {
                if (!services.isEmpty()) {
                    return ResponseEntity.ok(mapper.toDTO(services));
                } else {
                    throw new TuitionNotFoundException();
                }
            }
        } catch (Exception e) {
            throw new GeneralBadRequestException("Selección de Datos", "Parámetros de consulta incorrectos");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TuitionDTO> findById(@PathVariable String id) {
        Tuition tuition = repository.findById(id).orElse(null);
        if (tuition == null) {
            throw new TuitionNotFountException(id);
        } else {
            return ResponseEntity.ok(mapper.toDTO(tuition));
        }
    }

    @PostMapping("/")
    public ResponseEntity<TuitionDTO> postTuition(@RequestBody TuitionDTO tuitionDTO) {
        Tuition tuition = mapper.toModel(tuitionDTO);
        Tuition tuitionInsert = repository.save(tuition);
        return ResponseEntity.ok(mapper.toDTO(tuitionInsert));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TuitionDTO> putTuition(@PathVariable String id, @RequestBody TuitionDTO tuitionDTO) {
        try {
            Tuition tuitionUpdated = repository.findById(id).orElse(null);
            if (tuitionUpdated == null) {
                throw new TuitionNotFountException(id);
            } else {

                tuitionUpdated.setId(tuitionDTO.getId());
                tuitionUpdated = repository.save(tuitionUpdated);

                return ResponseEntity.ok(mapper.toDTO(tuitionUpdated));
            }
        } catch (Exception e) {
            throw new GeneralBadRequestException("Actualizar", "Error al actualizar el service. Campos incorrectos.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TuitionDTO> delete(@PathVariable String id) {
        Tuition tuition = repository.findById(id).orElse(null);
        if (tuition == null) {
            throw new TuitionNotFountException(id);
        }
        try {
            repository.delete(tuition);
            return ResponseEntity.ok(mapper.toDTO(tuition));
        } catch (Exception e) {
            throw new GeneralBadRequestException("Eliminar", "Error al borrar el service");
        }
    }
}
