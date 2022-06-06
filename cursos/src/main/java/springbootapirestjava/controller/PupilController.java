package springbootapirestjava.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import springbootapirestjava.config.APIConfig;
import springbootapirestjava.dto.pupil.PupilDTO;
import springbootapirestjava.dto.pupil.ResultPupilDTO;
import springbootapirestjava.mapper.PupilMapper;
import springbootapirestjava.model.Pupil;
import springbootapirestjava.repositories.PupilRepository;
import springbootapirestjava.service.StorageService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(APIConfig.API_PATH + "/pupil")
public class PupilController {

    private final PupilRepository repository;
    private final PupilMapper mapper;
    private final StorageService storageService;

    @Autowired
    public PupilController(PupilRepository repository, PupilMapper mapper, StorageService storageService) {
        this.repository = repository;
        this.mapper = mapper;
        this.storageService = storageService;
    }

    @GetMapping("/")
    public ResponseEntity<ResultPupilDTO> getAll() {
        List<Pupil> trainers = repository.findAll();
        ResultPupilDTO result = new ResultPupilDTO(mapper.toDTO(trainers));
        return ResponseEntity.status(HttpStatus.OK)
                .body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultPupilDTO> findById(@PathVariable String id) {
        Pupil pupil = repository.findById(id).orElse(null);
        ResultPupilDTO result;
        if (pupil != null) {
            result = new ResultPupilDTO(mapper.toDTO(List.of(pupil)));
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else {
            result = new ResultPupilDTO(mapper.toDTO(new ArrayList<>()));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
    }

    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PupilDTO> postPupil(@RequestBody PupilDTO pupilDTO,
                                               @RequestPart("file") MultipartFile file) {
        String urlImagen = null;
        if (!file.isEmpty()) {
            String imagen = storageService.store(file);
            urlImagen = MvcUriComponentsBuilder
                    .fromMethodName(FilesRestController.class, "serveFile", imagen, null)
                    .build().toUriString();
        }

        Pupil nuevoAlumno = new Pupil();
        nuevoAlumno.setName(pupilDTO.getName());
        nuevoAlumno.setEmail(pupilDTO.getEmail());
        nuevoAlumno.setImage(urlImagen);
        return ResponseEntity.ok(mapper.toDTO(nuevoAlumno));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultPupilDTO> putPupil(@PathVariable String id, @RequestBody PupilDTO pupilDTO) {
        Optional<Pupil> pupil = repository.findById(id);
        pupilDTO.setId(id);
        if (pupil.isPresent())
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResultPupilDTO(List.of(mapper.toDTO(repository.save(mapper.toModel(pupilDTO))))));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResultPupilDTO(new ArrayList<>()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResultPupilDTO> delete(@PathVariable String id) {
        Optional<Pupil> pupil = repository.findById(id);
        if (pupil.isPresent()) {
            repository.delete(pupil.get());
            return ResponseEntity.ok(new ResultPupilDTO(List.of(mapper.toDTO(pupil.get()))));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResultPupilDTO(new ArrayList<>()));
    }
}
