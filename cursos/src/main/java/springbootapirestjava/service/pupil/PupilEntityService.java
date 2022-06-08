package springbootapirestjava.service.pupil;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import springbootapirestjava.dto.pupil.CreatePupilDTO;
import springbootapirestjava.exceptions.GeneralBadRequestException;
import springbootapirestjava.exceptions.pupil.PupilNotFoundException;
import springbootapirestjava.model.Pupil;
import springbootapirestjava.model.PupilRol;
import springbootapirestjava.repositories.PupilRepository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PupilEntityService{

    private final PupilRepository repository;
    private final PasswordEncoder passwordEncoder;

    public Optional<Pupil> findByName(String name) {
        return repository.findByName(name);
    }

    public List<Pupil> findByNameContains(String name) {
        return repository.findByNameContains(name);
    }

    public List<Pupil> findAll() {
        return repository.findAll();
    }

    public Optional<Pupil> findById(String id) {
        return repository.findById(id);
    }

    public Pupil findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Optional<Pupil> findPupilById(String id) {
        return repository.findById(id);
    }

    public Pupil save(CreatePupilDTO newPupil) {
        if (newPupil.getPassword().equals(newPupil.getPassword2())) {
            Set<PupilRol> defaultRoles = new HashSet<>();
            defaultRoles.add(PupilRol.PUPIL);
            Pupil pupil = Pupil.builder()
                    .id(UUID.randomUUID().toString())
                    .name(newPupil.getName())
                    .email(newPupil.getEmail())
                    .password(passwordEncoder.encode(newPupil.getPassword()))
                    .image(newPupil.getImage())
                    .rols(defaultRoles)
                    .build();
            try {
                return repository.save(pupil);
            } catch (DataIntegrityViolationException ex) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre del alumno ya existe");
            }
        } else {
            throw new PupilNotFoundException();
        }
    }
}
