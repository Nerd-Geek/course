package springbootapirestjava.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springbootapirestjava.config.APIConfig;
import springbootapirestjava.dto.module.ModuleDTO;
import springbootapirestjava.dto.module.ResultModuleDTO;
import springbootapirestjava.mapper.ModuleMapper;
import springbootapirestjava.model.Modulo;
import springbootapirestjava.repositories.ModuleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(APIConfig.API_PATH + "/modulo")
public class ModuloController {

    private final ModuleRepository repository;
    private final ModuleMapper mapper;

    @GetMapping("/")
    public ResponseEntity<ResultModuleDTO> getAll() {
        List<Modulo> modulos = repository.findAll();
        ResultModuleDTO result = new ResultModuleDTO(mapper.toDTO(modulos));
        return ResponseEntity.status(HttpStatus.OK)
                .body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultModuleDTO> findById(@PathVariable String id) {
        Modulo modulo = repository.findById(id).orElse(null);
        ResultModuleDTO result;
        if (modulo != null) {
            result = new ResultModuleDTO(mapper.toDTO(List.of(modulo)));
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else {
            result = new ResultModuleDTO(mapper.toDTO(new ArrayList<>()));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
    }

    @PostMapping("/")
    public ResponseEntity<ModuleDTO> postModule(@RequestBody ModuleDTO moduleDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toDTO(repository.save(mapper.toModel(moduleDTO))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultModuleDTO> putModule(@PathVariable String id, @RequestBody ModuleDTO moduleDTO) {
        Optional<Modulo> modulo = repository.findById(id);
        moduleDTO.setId(id);
        if (modulo.isPresent())
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResultModuleDTO(List.of(mapper.toDTO(repository.save(mapper.toModel(moduleDTO))))));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResultModuleDTO(new ArrayList<>()));
    }
}
