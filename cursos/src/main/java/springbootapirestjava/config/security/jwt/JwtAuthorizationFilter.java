package springbootapirestjava.config.security.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import springbootapirestjava.model.Pupil;
import springbootapirestjava.service.pupil.CustomPupilService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;
    private final CustomPupilService customPupilService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws
            ServletException, IOException {
        try {
            String token = getJwtFromRequest(request);
            if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
                String pupilId = tokenProvider.getPupilIdFromJWT(token);
                Pupil pupil = (Pupil) customPupilService.loadPupilById(pupilId);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(pupil,
                        pupil.getRols(), pupil.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
        } catch (Exception ex) {
            log.info("No se ha podido establecer la autenticación de alumno en el contexto de seguridad");
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(JwtTokenProvider.TOKEN_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtTokenProvider.TOKEN_PREFIX)) {
            return bearerToken.substring(JwtTokenProvider.TOKEN_PREFIX.length());
        }
        return null;
    }
}