package springbootapirestjava.service.pupil;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import springbootapirestjava.exceptions.pupil.PupilNotFoundException;
import springbootapirestjava.model.Pupil;

@Service("userDetailsService")
@RequiredArgsConstructor
public class CustomPupilService implements UserDetailsService {
    private final PupilEntityService pupilEntityService;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        return pupilEntityService.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException(name + "no encontrado"));
    }

    public UserDetails loadPupilById(String id) {
        return pupilEntityService.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Alumno con id: " + id + " no encontrado"));
    }
}
