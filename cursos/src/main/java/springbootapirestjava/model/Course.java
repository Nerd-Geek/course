package springbootapirestjava.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
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
    private LocalDate createdAt;

    public Course(NamberCourse namberCourse, String acronym, LocalDate createdAt) {
        this.id = UUID.randomUUID().toString();
        this.namberCourse = namberCourse;
        this.acronym = acronym;
        this.createdAt = createdAt;
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
    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}
