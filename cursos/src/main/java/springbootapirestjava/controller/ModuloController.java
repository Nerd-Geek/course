package springbootapirestjava.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springbootapirestjava.config.APIConfig;
import springbootapirestjava.dto.module.ModuleDTO;
import springbootapirestjava.dto.module.ResultModuleDTO;
import springbootapirestjava.exceptions.GeneralBadRequestException;
import springbootapirestjava.exceptions.module.ModuleNotFoundException;
import springbootapirestjava.exceptions.module.ModuleNotFountException;
import springbootapirestjava.mapper.ModuleMapper;
import springbootapirestjava.model.Modulo;
import springbootapirestjava.repositories.ModuleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(APIConfig.API_PATH + "/modulo")
public class ModuloController {

    private final ModuleRepository repository;
    private final ModuleMapper mapper;

    @Autowired
    public ModuloController(ModuleRepository repository, ModuleMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @GetMapping("/")
    public ResponseEntity<List<ModuleDTO>> getAll(@RequestParam(name = "limit") Optional<String> limit) {
        List<Modulo> modulos = null;
        try {
            modulos = repository.findAll();

            if (limit.isPresent() && !modulos.isEmpty() && modulos.size() > Integer.parseInt(limit.get())) {
                return ResponseEntity.ok(mapper.toDTO(modulos.subList(0, Integer.parseInt(limit.get()))));
            } else {
                if (!modulos.isEmpty()) {
                    return ResponseEntity.ok(mapper.toDTO(modulos));
                } else {
                    throw new ModuleNotFoundException();
                }
            }
        } catch (Exception e) {
            throw new GeneralBadRequestException("Selección de Datos", "Parámetros de consulta incorrectos");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModuleDTO> findById(@PathVariable String id) {
        Modulo modulo = repository.findById(id).orElse(null);
        if (modulo == null) {
            throw new ModuleNotFountException(id);
        } else {
            return ResponseEntity.ok(mapper.toDTO(modulo));
        }
    }

    @PostMapping("/")
    public ResponseEntity<ModuleDTO> postModule(@RequestBody ModuleDTO moduleDTO) {
        Modulo modulo = mapper.toModel(moduleDTO);
        Modulo moduloInsert = repository.save(modulo);
        return ResponseEntity.ok(mapper.toDTO(moduloInsert));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModuleDTO> putModule(@PathVariable String id, @RequestBody ModuleDTO moduleDTO) {
        try {
            Modulo moduloUpdated = repository.findById(id).orElse(null);
            if (mapper == null) {
                throw new ModuleNotFountException(id);
            } else {
                moduloUpdated.setName(moduleDTO.getName());
                moduloUpdated.setAcronym(moduleDTO.getAcronym());
                moduloUpdated.setCreatedAt(moduleDTO.getCreateAt());
                moduloUpdated = repository.save(moduloUpdated);

                return ResponseEntity.ok(mapper.toDTO(moduloUpdated));
            }
        } catch (Exception e) {
            throw new GeneralBadRequestException("Actualizar", "Error al actualizar el service. Campos incorrectos.");
        }
    }
}
