package com.tecup.backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "careers")
public class Career {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max =100)
    private String name;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department_id;

    public Career() {
    }

    public Career(Long id, String name, Department department_id) {
        this.id = id;
        this.name = name;
        this.department_id = department_id;
    }
}
