package springbootapirestjava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import springbootapirestjava.model.Clase;

import java.util.UUID;

public interface ClaseRepository extends JpaRepository<Clase, String> {

}
