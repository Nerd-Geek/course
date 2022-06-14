package springbootapirestjava.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "clase")
@ToString
public class Clase {
    private String id;
    private Double clasification;
    @ToString.Exclude
    private Set<Modulo> modules;
    @ToString.Exclude
    private Set<Pupil> pupils;

    public Clase(Double clasification, Set<Modulo> modules, Set<Pupil> pupils) {
        this.id = UUID.randomUUID().toString();
        this.clasification = clasification;
        this.modules = modules;
        this.pupils = pupils;
    }

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getClasification() {
        return clasification;
    }

    public void setClasification(Double clasification) {
        this.clasification = clasification;
    }

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER)
    public Set<Modulo> getModules() {
        return modules;
    }

    public void setModules(Set<Modulo> modules) {
        this.modules = modules;
    }

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER)
    public Set<Pupil> getPupils() {
        return pupils;
    }

    public void setPupils(Set<Pupil> pupils) {
        this.pupils = pupils;
    }
}
