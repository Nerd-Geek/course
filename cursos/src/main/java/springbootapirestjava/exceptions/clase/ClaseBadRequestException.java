package springbootapirestjava.exceptions.clase;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ClaseBadRequestException extends RuntimeException {
    private static final long serialVersionUID = 86546786467580532L;

    public ClaseBadRequestException(String campo, String error) {
        super("Existe un error en el campo: " + campo + " Error: " + error);
    }
}
