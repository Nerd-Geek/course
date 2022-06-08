package springbootapirestjava.exceptions.module;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ModuleNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 86546786467580532L;

    public ModuleNotFoundException() {
        super("La lista de logins está vacía o no existe");
    }
}

