package springbootapirestjava.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import springbootapirestjava.config.APIConfig;
import springbootapirestjava.config.security.jwt.JwtTokenProvider;
import springbootapirestjava.config.security.jwt.model.JwtUserResponse;
import springbootapirestjava.config.security.jwt.model.LoginRequest;
import springbootapirestjava.dto.pupil.CreatePupilDTO;
import springbootapirestjava.dto.pupil.PupilDTO;
import springbootapirestjava.exceptions.GeneralBadRequestException;
import springbootapirestjava.exceptions.pupil.PupilNotFoundException;
import springbootapirestjava.exceptions.pupil.PupilNotFountException;
import springbootapirestjava.mapper.PupilMapper;
import springbootapirestjava.model.Pupil;
import springbootapirestjava.model.PupilRol;
import springbootapirestjava.repositories.PupilRepository;
import springbootapirestjava.service.pupil.PupilEntityService;
import springbootapirestjava.service.update.StorageService;

import javax.management.ServiceNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(APIConfig.API_PATH + "/pupil")
@RequiredArgsConstructor
public class PupilController {

    private final PupilMapper mapper;
    private final StorageService storageService;
    private final PupilEntityService pupilEntityService;

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    @GetMapping("/")
    public ResponseEntity<List<?>> getAll(@RequestParam("searchQuery") Optional<String> searchQuery) {
        List<Pupil> pupils;
        try {
            if (searchQuery.isPresent())
                pupils = pupilEntityService.findByNameContains(searchQuery.get());
            else
                pupils = pupilEntityService.findAll();
            return ResponseEntity.ok(mapper.toDTO(pupils));
        } catch (Exception e) {
            throw new GeneralBadRequestException("Selección de Datos", "Parámetros de consulta incorrectos");
        }
    }

    @GetMapping("/name/{username}")
    public ResponseEntity<?> findByName(@PathVariable String name) {
        Pupil pupil = pupilEntityService.findByName(name).orElse(null);
        if (pupil == null) {
            throw new PupilNotFoundException();
        } else {
            return ResponseEntity.ok(mapper.toDTO(pupil));
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable String email) {
        Pupil pupil = pupilEntityService.findByEmail(email);
        if (pupil == null) {
            throw new PupilNotFoundException();
        } else {
            return ResponseEntity.ok(mapper.toDTO(pupil));
        }
    }

    @GetMapping("/me")
    public PupilDTO me(@AuthenticationPrincipal Pupil pupil) {
        return mapper.toDTO(pupil);
    }

    @PostMapping("/login")
    public JwtUserResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getName(),
                                loginRequest.getPassword()
                        )
                );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Pupil pupil = (Pupil) authentication.getPrincipal();
        String jwtToken = tokenProvider.generateToken(authentication);
        return convertPupilEntityAndTokenToJwtUserResponse(pupil, jwtToken);
    }

    @PostMapping("/")
    public PupilDTO newPupil(@RequestBody CreatePupilDTO newPupil) {
        return mapper.toDTO(pupilEntityService.save(newPupil));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        Pupil pupil = pupilEntityService.findById(id).orElse(null);
        if (pupil == null) {
            throw new PupilNotFountException(id);
        } else {
            return ResponseEntity.ok(mapper.toDTO(pupil));
        }
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> postPupil(@RequestPart("user") CreatePupilDTO newPupil,
                                       @RequestPart("file") MultipartFile file) {

        Pupil pupil = mapper.fromDTOCreate(newPupil);

        if (!file.isEmpty()) {
            String imagen = storageService.store(file);
            String urlImagen = storageService.getUrl(imagen);
            pupil.setImage(urlImagen);
            newPupil.setImage(pupil.getImage());
        }
        try {
            Pupil pupilInsertado = pupilEntityService.save(newPupil);
            return ResponseEntity.ok(mapper.toDTO(pupilInsertado));
        } catch (Exception ex) {
            throw new GeneralBadRequestException("Insertar", "Error al insertar el producto. Campos incorrectos");
        }
    }

    private JwtUserResponse convertPupilEntityAndTokenToJwtUserResponse(Pupil pupil, String jwtToken) {
        return JwtUserResponse
                .jwtUserResponseBuilder()
                .id(pupil.getId())
                .image(pupil.getImage())
                .name(pupil.getName())
                .userRoles(pupil.getRols().stream().map(PupilRol::name).collect(Collectors.toSet()))
                .email(pupil.getEmail())
                .token(jwtToken)
                .build();
    }
}
