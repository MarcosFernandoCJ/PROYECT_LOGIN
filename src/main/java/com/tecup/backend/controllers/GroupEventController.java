package com.tecup.backend.controllers;

import com.tecup.backend.models.GroupEvent;
import com.tecup.backend.payload.repository.GroupEventRepository;
import com.tecup.backend.payload.response.GroupEventResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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
                        groupEvent.getName(), // Nombre del grupo
                        groupEvent.getEvent().getName(), // Nombre del evento
                        groupEvent.getDepartment().getName(), // Nombre del departamento
                        groupEvent.getEvent().getMax_participants_group() // Máximo de participantes por grupo
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

}
