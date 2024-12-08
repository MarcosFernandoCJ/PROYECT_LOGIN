package com.tecup.backend.controllers;

import com.tecup.backend.models.GroupEvent;
import com.tecup.backend.models.Jury;
import com.tecup.backend.models.Score;
import com.tecup.backend.payload.repository.GroupEventRepository;
import com.tecup.backend.payload.repository.ScoreRepository;
import com.tecup.backend.payload.response.GroupEventResponse;
import com.tecup.backend.payload.response.MessageResponse;
import com.tecup.backend.payload.response.ScoreResponse;
import com.tecup.backend.payload.response.ScoreTopResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/group-events")
public class GroupEventController {

    private static final Logger logger = LoggerFactory.getLogger(GroupEventController.class);
    @Autowired
    private GroupEventRepository groupEventRepository;

    @GetMapping("/all")
    @PreAuthorize("hasRole('USER') or hasRole('ORGANIZADOR') or hasRole('ADMIN') or hasRole('JURADO')")
    public ResponseEntity<List<GroupEventResponse>> getAllGroupEvents() {
        List<GroupEventResponse> responses = groupEventRepository.findAll().stream()
                .map(groupEvent -> new GroupEventResponse(
                        groupEvent.getId(),
                        groupEvent.getName(), // Nombre del grupo
                        groupEvent.getEvent().getName(), // Nombre del evento
                        groupEvent.getDepartment().getName(), // Nombre del departamento
                        groupEvent.getEvent().getMax_participants_group() // Máximo de participantes por grupo
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }


    @Autowired
    private ScoreRepository scoreRepository;

    @GetMapping("/top")
    public ResponseEntity<List<ScoreTopResponse>> getTopScores() {
        List<Score> scores = scoreRepository.findAll(); // Obtener todos los puntajes

        // Agrupar los puntajes por grupo y sumar los puntajes
        Map<String, Integer> groupedScores = scores.stream()
                .collect(Collectors.groupingBy(
                        score -> score.getGroupEvent().getName(),
                        Collectors.summingInt(Score::getScore)
                ));

            // Convertir el resultado a una lista de ScoreTopResponse
            List<ScoreTopResponse> response = groupedScores.entrySet().stream()
                    .map(entry -> new ScoreTopResponse(entry.getValue(), entry.getKey()))
                    .sorted((a, b) -> b.getScore() - a.getScore()) // Ordenar de mayor a menor puntaje
                    .collect(Collectors.toList());

            return ResponseEntity.ok(response);
        }


}
