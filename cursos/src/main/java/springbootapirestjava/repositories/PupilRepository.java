package springbootapirestjava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import springbootapirestjava.model.Pupil;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PupilRepository extends JpaRepository<Pupil, String> {
    Optional<Pupil> findByName(String name);
    Optional<Pupil> findByEmail(String name);
}
