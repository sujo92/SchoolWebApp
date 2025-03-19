package com.example.SchoolWebApp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "class")
public class EClass extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy="native")
    private int classId;

    @NotBlank(message="name must not be blank")
    @Size(min=3, message="name must be at least 3 chars long")
    private String name;

    @OneToMany(mappedBy = "eClass", fetch=FetchType.LAZY, cascade = CascadeType.PERSIST, targetEntity = Person.class)
    private Set<Person> persons;
}
