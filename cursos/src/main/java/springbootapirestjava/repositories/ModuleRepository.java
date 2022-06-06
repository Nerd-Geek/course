package springbootapirestjava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import springbootapirestjava.model.Modulo;

import java.util.UUID;

public interface ModuleRepository extends JpaRepository<Modulo, String> {
}
