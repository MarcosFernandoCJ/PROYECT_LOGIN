package com.tecup.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "inscriptions")
public class Inscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Date fecha_inscripcion;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "group_event_id", nullable = true)
    private GroupEvent group; // Referencia al grupo al que pertenece la inscripción

    public Inscription() {}

    public Inscription(Date fecha_inscripcion, Event event, User user, GroupEvent group) {
        this.fecha_inscripcion = fecha_inscripcion;
        this.event = event;
        this.user = user;
        this.group = group;
    }
}
