package com.tecup.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Jurys")
public class Jury {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User jurado; // Usuario con el rol de jurado.

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event; // Evento asignado al jurado.

    public Jury() {}

    public Jury(Long id, User jurado, Event event) {
        this.id = id;
        this.jurado = jurado;
        this.event = event;
    }
}
