package springbootapirestjava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import springbootapirestjava.model.Tuition;

import java.util.UUID;

public interface TuitionRepository extends JpaRepository<Tuition, String> {
}
