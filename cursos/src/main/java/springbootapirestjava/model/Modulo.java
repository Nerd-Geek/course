package springbootapirestjava.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "module")
@ToString
public class Modulo {
    private String id;
    private String name;
    @Column(unique = true)
    private String acronym;
    private Date createdAt;
    @ToString.Exclude
    private Course course;
    @ToString.Exclude
    private Clase clase;

    public Modulo(String name, String acronym, Date createdAt, Course course, Clase clase) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.acronym = acronym;
        this.createdAt = createdAt;
        this.course = course;
        this.clase = clase;
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

    @Column(name = "acronym")
    @NotNull(message = "El acronimo no puede ser nulo")
    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    @Column(name = "createdAt")
    @NotNull(message = "El createdAt no puede ser nulo")
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_course", referencedColumnName = "id")
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
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
}
