package springbootapirestjava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import springbootapirestjava.model.Pupil;

import java.util.List;
import java.util.Optional;

public interface PupilRepository extends JpaRepository<Pupil, String> {
    Optional<Pupil> findByName(String name);
    List<Pupil> findByNameContains(String name);
    Pupil findByEmail(String name);
}
