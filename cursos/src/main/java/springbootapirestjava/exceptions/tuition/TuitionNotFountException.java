package springbootapirestjava.exceptions.tuition;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TuitionNotFountException extends RuntimeException {
    private static final long serialVersionUID = 86546786467580532L;

    public TuitionNotFountException(String id) {
        super("No se ha encontrado el service con la ID: " + id);
    }
}
