package com.app.emsx.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "skills",
        uniqueConstraints = @UniqueConstraint(name = "uk_skill_name", columnNames = "name"))
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=100)
    private String name;

    @Column(length=300)
    private String description;

    // Lado inverso para evitar recursión en JSON
    @ManyToMany(mappedBy = "skills")
    @JsonIgnore
    private Set<Employee> employees = new HashSet<>();
}
