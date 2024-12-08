package com.tecup.backend.controllers;

import com.tecup.backend.models.*;
import com.tecup.backend.payload.repository.*;
import com.tecup.backend.payload.request.InscriptionRequest;
import com.tecup.backend.payload.response.InscriptionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.Set;
import java.util.stream.Collectors;
import java.security.Principal;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/inscriptions")
public class InscriptionController {
    private static final Logger logger = LoggerFactory.getLogger(InscriptionController.class);
    @Autowired
    private InscriptionRepository inscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private GroupEventRepository groupEventRepository;



    //este se eliminara
    @GetMapping("/all")
    @PreAuthorize("hasRole('USER') or hasRole('ORGANIZADOR') or hasRole('ADMIN') or hasRole('JURADO')")
    public List<InscriptionResponse> getAllInscriptions() {


        return inscriptionRepository.findAll().stream().map(inscription -> {

            return new InscriptionResponse(
                    inscription.getId(), // Incluir el ID de la inscripción
                    inscription.getUser().getUsername(),
                    inscription.getEvent().getName(),
                    inscription.getFecha_inscripcion().toString()
            );
        }).collect(Collectors.toList());
    }


    //Para ver las incripciones las isncripciones que tiene el usuairo logeado (debe de tener el rol USER)
    @GetMapping("/user-inscriptions")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUserInscriptions() {
        // Obtener el usuario autenticado
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Usuario autenticado no encontrado.");
        }

        // Obtener las inscripciones del usuario autenticado
        Set<Inscription> userInscriptions = user.get().getInscriptions();

        // Mapear las inscripciones a InscriptionResponse
        List<InscriptionResponse> inscriptionResponses = userInscriptions.stream()
                .map(inscription -> new InscriptionResponse(
                        inscription.getId(),
                        inscription.getUser().getUsername(),
                        inscription.getEvent().getName(),
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(inscription.getFecha_inscripcion())
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(inscriptionResponses);
    }



    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createInscription(@RequestBody InscriptionRequest inscriptionRequest) {
        // Obtener el usuario autenticado desde el contexto de seguridad
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Usuario autenticado no encontrado.");
        }

        // Validar el evento especificado en la solicitud
        Optional<Event> event = eventRepository.findById(inscriptionRequest.getEventId());
        if (event.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Evento especificado no encontrado.");
        }

        // Validar si el usuario ya está inscrito en el evento
        boolean alreadyInscribed = inscriptionRepository.existsByUserAndEvent(user.get(), event.get());
        if (alreadyInscribed) {
            return ResponseEntity.badRequest().body("Error: El usuario ya está inscrito en este evento.");
        }

        // Obtener el departamento asociado a la carrera del usuario
        if (user.get().getCareer() == null || user.get().getCareer().getDepartment_id() == null) {
            return ResponseEntity.badRequest().body("Error: El usuario no tiene un departamento asociado.");
        }
        Department userDepartment = user.get().getCareer().getDepartment_id();

        // Buscar el grupo correspondiente basado en el evento y el departamento del usuario
        Optional<GroupEvent> groupEvent = groupEventRepository.findByEventAndDepartment(event.get(), userDepartment);
        if (groupEvent.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: No se encontró un grupo para este evento y departamento.");
        }

        // Validar si el grupo tiene espacio disponible
        int currentGroupParticipants = groupEvent.get().getInscriptions().size();
        int maxParticipantsGroup = event.get().getMax_participants_group();

        if (currentGroupParticipants >= maxParticipantsGroup) {
            return ResponseEntity.badRequest().body("Error: El grupo ha alcanzado el límite máximo de participantes.");
        }

        // Crear la inscripción
        Inscription inscription = new Inscription();
        inscription.setUser(user.get()); // Asociar el usuario autenticado
        inscription.setEvent(event.get()); // Asociar el evento
        inscription.setGroup(groupEvent.get()); // Asociar el grupo
        inscription.setFecha_inscripcion(new Date()); // Establecer la fecha actual

        // Guardar la inscripción
        inscriptionRepository.save(inscription);

        // Construir la respuesta
        return ResponseEntity.ok("Inscripción creada exitosamente.");
    }


    //Elimina inscripción que selecione el usuario de las inscriociones que tiene
    @DeleteMapping("/delete/{inscriptionId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteUserInscription(@PathVariable("inscriptionId") long inscriptionId) {
        // Obtener el usuario autenticado
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Usuario autenticado no encontrado.");
        }

        // Verificar que la inscripción existe
        Optional<Inscription> inscription = inscriptionRepository.findById(inscriptionId);
        if (inscription.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Inscripción no encontrada.");
        }

        // Validar que la inscripción pertenece al usuario autenticado
        if (!inscription.get().getUser().getId().equals(user.get().getId())) {
            return ResponseEntity.status(403).body("Error: No tienes permiso para eliminar esta inscripción.");
        }

        // Eliminar la inscripción
        inscriptionRepository.delete(inscription.get());

        return ResponseEntity.ok("La inscripción ha sido eliminada exitosamente.");
    }





}
