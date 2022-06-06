package springbootapirestjava.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "pupil")
@ToString
public class Pupil {
    private String id;
    private String name;
    @Column(unique = true)
    private String email;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private String image;
    @ToString.Exclude
    private Clase clase;
    @ToString.Exclude
    private Tuition tuition;

    public Pupil(String name, String email, LocalDate updatedAt, String image, Clase clase, Tuition tuition) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.updatedAt = updatedAt;
        this.image = image;
    }

    public Pupil(String name, String email) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
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
    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    @Column(name = "updatedAt")
    @NotNull(message = "El createdAt no puede ser nulo")
    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
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
    @JoinColumn(name = "id_clase", referencedColumnName = "id")
    public Clase getClase() {
        return clase;
    }

    public void setClase(Clase clase) {
        this.clase = clase;
    }

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_tuition", referencedColumnName = "id")
    public Tuition getTuition() {
        return tuition;
    }

    public void setTuition(Tuition tuition) {
        this.tuition = tuition;
    }
}
