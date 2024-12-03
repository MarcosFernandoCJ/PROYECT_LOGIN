package com.tecup.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "scores")
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "jury_id", nullable = false)
    private Jury jury; // El jurado que asigna el puntaje

    @ManyToOne
    @JoinColumn(name = "group_event_id", nullable = false)
    private GroupEvent groupEvent; // El grupo que recibe el puntaje

    private int score; // El puntaje asignado

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPuntaje; // Fecha en la que se asignó el puntaje

    public Score() {}

    public Score(Jury jury, GroupEvent groupEvent, int score, Date fechaPuntaje) {
        this.jury = jury;
        this.groupEvent = groupEvent;
        this.score = score;
        this.fechaPuntaje = fechaPuntaje;
    }
}
