package springbootapirestjava.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springbootapirestjava.config.APIConfig;
import springbootapirestjava.dto.clase.ClaseDTO;
import springbootapirestjava.dto.clase.ResultClaseDTO;
import springbootapirestjava.exceptions.GeneralBadRequestException;
import springbootapirestjava.exceptions.clase.ClaseNotFoundException;
import springbootapirestjava.exceptions.clase.ClaseNotFountException;
import springbootapirestjava.mapper.ClaseMapper;
import springbootapirestjava.model.Clase;
import springbootapirestjava.repositories.ClaseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(APIConfig.API_PATH + "/clase")
public class ClaseController {

    private final ClaseRepository repository;
    private final ClaseMapper mapper;

    @Autowired
    public ClaseController(ClaseRepository repository, ClaseMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @GetMapping("/")
    public ResponseEntity<List<ClaseDTO>> findAll(@RequestParam(name = "limit") Optional<String> limit) {
        List<Clase> clases = null;

        try {
            clases = repository.findAll();
            if (limit.isPresent() && !clases.isEmpty() && clases.size() > Integer.parseInt(limit.get())) {
                return ResponseEntity.ok(mapper.toDTO(clases.subList(0, Integer.parseInt(limit.get()))));
            } else {
                if (!clases.isEmpty()) {
                    return ResponseEntity.ok(mapper.toDTO(clases));
                } else {
                    throw new ClaseNotFoundException();
                }
            }
        } catch (Exception e) {
            throw new GeneralBadRequestException("Selección de Datos", "Parámetros de consulta incorrectos");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClaseDTO> findById(@PathVariable String id) {
        Clase clase = repository.findById(id).orElse(null);
        if (clase == null) {
            throw new ClaseNotFountException(id);
        } else {
            return ResponseEntity.ok(mapper.toDTO(clase));
        }
    }

    @PostMapping("/")
    public ResponseEntity<ClaseDTO> postClase(@RequestBody ClaseDTO newClase) {
        Clase clase = mapper.toModel(newClase);
        Clase claseInsert = repository.save(clase);
        return ResponseEntity.ok(mapper.toDTO(claseInsert));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClaseDTO> putClase(@PathVariable String id, @RequestBody ClaseDTO claseDTO) {
        try {
            Clase claseUpdated = repository.findById(id).orElse(null);
            if (claseUpdated == null) {
                throw new ClaseNotFountException(id);
            } else {
                claseUpdated.setClasification(claseDTO.getClasification());
                claseUpdated = repository.save(claseUpdated);

                return ResponseEntity.ok(mapper.toDTO(claseUpdated));
            }
        } catch (Exception e) {
            throw new GeneralBadRequestException("Actualizar", "Error al actualizar el service. Campos incorrectos.");
        }
    }
}
