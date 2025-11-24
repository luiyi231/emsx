package com.app.emsx.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "employees")
@Data @NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String phone;
    /*
     *En JPA,  y sobre todo en proyectos con spring boot, los FetchType definen
     *la manera como y cuando se cargan las relaciones entre clases/entidades.
     * Esto desde la BASE DE DATOS (En nuestro caso desde la BD que creamos con MySQL)
     * FetchType.LAZY:Esto se conoce como carga perezosa. Lo que que quiere decir que la relación
     * entre las entidades participantes no se cargan de manera inmediata, esto cuando se obtiene
     * la entidad principal (Entidad Principal se refiere en este caso a Employee o quien tiene la primera relación).
     * Lo que quiere decir que se carga cuando realmente se accede a esa
     * relación, en este caso entre employee y department. Estos es muy util para cuando queremos
     * optimizar el rendimiento; por ejemplo, esto evita consultas que no son necesarias a la base de datos.
     *
     * FetchType:EAGER.- En este caso, las relaciones se cargar de manera inmediata, lo que quiere decir
     * que la relación se carga la mismo tiempo que la entidad principal (en este caso para nosotros la
     * entidad principal se llama Employee). Esto implica que se hará un JOIN o en algunos casos múltiples
     * consultas de manera automática. Debemos recordar que esto puede provocar cargas innecesarias si no siempre
     * se usas esas relaciones.
     * RESUMEN:
     * FetchType.LAZY.- Carga diferida. Solo se recomienda para la mayoría de las colecciones (@OneToMany, y
     * tambien en la relación @ManyToMany
     * FetchType:EAGER.- Carga inmediata. Se debe usar en relaciones que siempre se necesita @ManyToOne, @OneToOne
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Dependent> dependents = new ArrayList<>();

    //Previenen la los ciclos infinitos. En las relaciones bidireccionales.

    public void addDependent(Dependent d) {
        dependents.add(d);
        d.setEmployee(this);
    }
    public void removeDependent(Dependent d) {
        dependents.remove(d);
        d.setEmployee(null);
    }


    //Nota: no usamos cascade = REMOVE en ManyToMany para no borrar Skill al quitarla de un empleado.
    @ManyToMany
    @JoinTable(
            name = "employee_skills",
            joinColumns = @JoinColumn(name = "employee_id",
                    foreignKey = @ForeignKey(name = "fk_emp_skill_employee")),
            inverseJoinColumns = @JoinColumn(name = "skill_id",
                    foreignKey = @ForeignKey(name = "fk_emp_skill_skill"))
    )
    private Set<Skill> skills = new HashSet<>();

    public void addSkill(Skill s){ this.skills.add(s); }
    public void removeSkill(Skill s){ this.skills.remove(s); }
    public void clearSkills(){ this.skills.clear(); }

}
