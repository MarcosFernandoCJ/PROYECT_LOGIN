package com.tecup.backend.controllers;

import com.tecup.backend.models.*;
import com.tecup.backend.payload.repository.*;
import com.tecup.backend.payload.request.ScoreRequest;
import com.tecup.backend.payload.response.MessageResponse;
import com.tecup.backend.payload.response.ScoreResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api/scores")
public class ScoreController {

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private JuryRepository juryRepository;

    @Autowired
    private GroupEventRepository groupEventRepository;

    @PostMapping("/add")
    @PreAuthorize("hasRole('JURADO')")
    public ResponseEntity<?> assignScore(@RequestBody ScoreRequest request) {
        // Obtener el usuario autenticado
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Validar si el usuario es un jurado
        Optional<Jury> juryOptional = juryRepository.findByJuradoUsername(username);
        if (juryOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: No se encontró el jurado para el usuario actual."));
        }

        Jury jury = juryOptional.get();

        // Validar el grupo
        Optional<GroupEvent> groupOptional = groupEventRepository.findById(request.getGroupId());
        if (groupOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Grupo no encontrado."));
        }

        GroupEvent group = groupOptional.get();

        // Validar que el grupo pertenece al evento asignado al jurado
        if (!jury.getEvent().equals(group.getEvent())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: No puedes calificar este grupo. El evento no coincide."));
        }

        // Validar que el jurado no haya calificado antes a este grupo
        Optional<Score> existingScore = scoreRepository.findByJuryAndGroupEvent(jury, group);
        if (existingScore.isPresent()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Ya has calificado este grupo."));
        }

        // Crear el nuevo puntaje
        Score newScore = new Score(jury, group, request.getScore(), new Date());
        scoreRepository.save(newScore);

        return ResponseEntity.ok(new MessageResponse("Puntaje asignado exitosamente."));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('JURADO')")
    public ResponseEntity<?> getAllScores() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Jury> juryOptional = juryRepository.findByJuradoUsername(username);

        if (juryOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: No se encontró el jurado para el usuario actual."));
        }

        Jury jury = juryOptional.get();

        return ResponseEntity.ok(
                scoreRepository.findAll().stream()
                        .filter(score -> score.getJury().equals(jury))
                        .map(score -> new ScoreResponse(
                                jury.getJurado().getUsername(),
                                score.getGroupEvent().getName(),
                                score.getScore(),
                                score.getFechaPuntaje()
                        ))
                        .toList()
        );
    }
}
