package springbootapirestjava.exceptions.pupil;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PupilNotFountException extends RuntimeException {
    private static final long serialVersionUID = 86546786467580532L;

    public PupilNotFountException(String id) {
        super("No se ha encontrado el service con la ID: " + id);
    }
}
