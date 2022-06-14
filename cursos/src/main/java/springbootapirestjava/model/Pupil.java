package springbootapirestjava.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@EntityListeners(AuditingEntityListener.class) // Fecha creación usuario automática
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "pupil")
@ToString
public class Pupil implements UserDetails {
    private String id;
    private String name;
    private String username;
    private String password;
    private Set<PupilRol> rols;
    @Column(unique = true)
    private String email;
    private Date createdAt;
    private Date updatedAt = Date.from(Instant.now());
    private String image;
    private Clase clase;
    private Tuition tuition;

    public Pupil(String name, String email, String image, String password, Clase clase, Tuition tuition, Set<PupilRol> rols, String username) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.image = image;
        this.rols = rols;
        this.username = username;
    }

    public Pupil(String name, String email, String password,Set<PupilRol> rols, String username) {
        this.id = UUID.randomUUID().toString();
        this.password = password;
        this.name = name;
        this.email = email;
        this.rols = rols;
        this.username = username;
    }

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "name")
    @NotNull(message = "El nombre no puede ser nulo")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    public Set<PupilRol> getRols() {
        return rols;
    }

    public void setRols(Set<PupilRol> rols) {
        this.rols = rols;
    }

    @Column(name = "email", unique = true)
    @Email(regexp = ".*@.*\\..*", message = "Email debe ser un email valido")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "createdAt")
    @NotNull(message = "El createdAt no puede ser nulo")
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Column(name = "updatedAt")
    @NotNull(message = "El createdAt no puede ser nulo")
    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @JsonBackReference
    @ManyToOne
    public Clase getClase() {
        return clase;
    }

    public void setClase(Clase clase) {
        this.clase = clase;
    }

    @JsonBackReference
    @ManyToOne
    public Tuition getTuition() {
        return tuition;
    }

    public void setTuition(Tuition tuition) {
        this.tuition = tuition;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Transient
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return rols
                .stream()
                .map(ur -> new SimpleGrantedAuthority("ROLE_:" + ur.name()))
                .collect(Collectors.toList());
    }

    @Transient
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Transient
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Transient
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Transient
    @Override
    public boolean isEnabled() {
        return true;
    }
}
