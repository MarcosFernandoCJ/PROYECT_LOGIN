package com.tecup.backend.controllers;

import com.tecup.backend.models.*;
import com.tecup.backend.payload.repository.*;
import com.tecup.backend.payload.request.ScoreRequest;
import com.tecup.backend.payload.response.AdminJuryResponse;
import com.tecup.backend.payload.response.MessageResponse;
import com.tecup.backend.payload.response.ScoreResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @GetMapping("/my-scores")
    @PreAuthorize("hasRole('JURADO')")
    public ResponseEntity<?> getMyScores() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Jury> juryOptional = juryRepository.findByJuradoUsername(username);

        if (juryOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: No se encontró el jurado para el usuario actual."));
        }

        Jury jury = juryOptional.get();

        List<ScoreResponse> scores = scoreRepository.findAll().stream()
                .filter(score -> score.getJury().equals(jury))
                .map(score -> new ScoreResponse(
                        jury.getJurado().getUsername(),
                        score.getGroupEvent().getName(),
                        score.getScore(),
                        score.getFechaPuntaje()
                ))
                .toList();

        return ResponseEntity.ok(scores);
    }

    /**
     * Actualizar un puntaje hecho por el usuario logeado con el rol de JURADO.
     */
    @PutMapping("/update/{scoreId}")
    @PreAuthorize("hasRole('JURADO')")
    public ResponseEntity<?> updateScore(@PathVariable("scoreId") Long scoreId, @RequestBody ScoreRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Jury> juryOptional = juryRepository.findByJuradoUsername(username);

        if (juryOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: No se encontró el jurado para el usuario actual."));
        }

        Jury jury = juryOptional.get();

        Optional<Score> scoreOptional = scoreRepository.findById(scoreId);

        if (scoreOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Puntaje no encontrado."));
        }

        Score score = scoreOptional.get();

        if (!score.getJury().equals(jury)) {
            return ResponseEntity.status(403).body(new MessageResponse("Error: No tienes permiso para actualizar este puntaje."));
        }

        score.setScore(request.getScore());
        score.setFechaPuntaje(new Date()); // Actualizar fecha automáticamente
        scoreRepository.save(score);

        return ResponseEntity.ok(new MessageResponse("Puntaje actualizado exitosamente."));
    }

    /**
     * Eliminar un puntaje hecho por el usuario logeado con el rol de JURADO.
     */
    @DeleteMapping("/delete/{scoreId}")
    @PreAuthorize("hasRole('JURADO')")
    public ResponseEntity<?> deleteScore(@PathVariable("scoreId") Long scoreId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Jury> juryOptional = juryRepository.findByJuradoUsername(username);

        if (juryOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: No se encontró el jurado para el usuario actual."));
        }

        Jury jury = juryOptional.get();

        Optional<Score> scoreOptional = scoreRepository.findById(scoreId);

        if (scoreOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Puntaje no encontrado."));
        }

        Score score = scoreOptional.get();

        if (!score.getJury().equals(jury)) {
            return ResponseEntity.status(403).body(new MessageResponse("Error: No tienes permiso para eliminar este puntaje."));
        }

        scoreRepository.delete(score);
        return ResponseEntity.ok(new MessageResponse("Puntaje eliminado exitosamente."));
    }

    @GetMapping("/alljurados")
    public ResponseEntity<List<AdminJuryResponse>> getAllJurors() {
        List<Jury> juries = juryRepository.findAll();

        // Mapear los jurados a AdminJuryResponse
        List<AdminJuryResponse> response = juries.stream().map(jury -> {
            // Si tienes el evento relacionado con el jurado, puedes usar el eventRepository para obtener su nombre.
            // Asegúrate de que tu entidad Jury tenga una relación con Event
            String eventName = jury.getEvent() != null ? jury.getEvent().getName() : "Evento no asignado";

            return new AdminJuryResponse(
                    jury.getJurado().getId(),
                    jury.getJurado().getUsername(),
                    jury.getEvent() != null ? jury.getEvent().getId() : null,
                    eventName,
                    "Mensaje o estado del jurado (opcional)"
            );
        }).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }



}

