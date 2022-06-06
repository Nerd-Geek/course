package springbootapirestjava.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "course")
@ToString
public class Course {
    private String id;
    private NamberCourse namberCourse;
    @Column(unique = true)
    private String acronym;
    @CreatedDate
    private Date createdAt;
    @ToString.Exclude
    private Set<Tuition> tuitions;
    @ToString.Exclude
    private Set<Modulo> modules;

    public Course(NamberCourse namberCourse, String acronym, Date createdAt, Set<Tuition> tuitions, Set<Modulo> modules) {
        this.id = UUID.randomUUID().toString();
        this.namberCourse = namberCourse;
        this.acronym = acronym;
        this.createdAt = createdAt;
        this.tuitions = tuitions;
        this.modules = modules;
    }

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Enumerated(EnumType.STRING)
    @NotNull(message = "El número del curso no puede ser nulo")
    public NamberCourse getNamberCourse() {
        return namberCourse;
    }

    public void setNamberCourse(NamberCourse namberCourse) {
        this.namberCourse = namberCourse;
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

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "course")
    public Set<Tuition> getTuitions() {
        return tuitions;
    }

    public void setTuitions(Set<Tuition> tuitions) {
        this.tuitions = tuitions;
    }

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "course")
    public Set<Modulo> getModules() {
        return modules;
    }

    public void setModules(Set<Modulo> modules) {
        this.modules = modules;
    }
}
