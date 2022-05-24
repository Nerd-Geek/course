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
@Table(name = "module")
@ToString
public class Module {
    private String id;
    private String name;
    @Column(unique = true)
    private String acronym;
    private LocalDate createdAt;

    public Module(String name, String acronym, LocalDate createdAt) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
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
    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}
