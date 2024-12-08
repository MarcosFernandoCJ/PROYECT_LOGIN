package com.tecup.backend.controllers;

import com.tecup.backend.models.*;
import com.tecup.backend.payload.repository.EventRepository;
import com.tecup.backend.payload.repository.JuryRepository;
import com.tecup.backend.payload.repository.RoleRepository;
import com.tecup.backend.payload.repository.UserRepository;
import com.tecup.backend.payload.request.AdminJuryRequest;
import com.tecup.backend.payload.request.AdminRequest;
import com.tecup.backend.payload.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')") // Solo los administradores pueden acceder
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private JuryRepository juryRepository;

    /**
     * Asignar roles ADMIN u ORGANIZADOR.
     */
    @PostMapping("/assign-role")
    public ResponseEntity<?> assignRole(@RequestBody AdminRequest request) {
        Optional<User> userOptional = userRepository.findById(request.getUserId());
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Usuario no encontrado.");
        }
        User user = userOptional.get();
        Set<Role> roles = user.getRoles();

        switch (request.getRoleName().toLowerCase()) {
            case "admin":
                Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException("Error: Rol ADMIN no encontrado."));
                if (roles.contains(adminRole)) {
                    return ResponseEntity.badRequest().body(
                            new AdminResponse(user.getId(), user.getUsername(), roles.stream().map(Role::getName).map(Enum::name).toList(),
                                    "Error: El usuario ya tiene el rol ADMIN."));
                }
                roles.add(adminRole);
                break;

            case "organizador":
                Role organizadorRole = roleRepository.findByName(ERole.ROLE_ORGANIZADOR)
                        .orElseThrow(() -> new RuntimeException("Error: Rol ORGANIZADOR no encontrado."));
                if (roles.contains(organizadorRole)) {
                    return ResponseEntity.badRequest().body(
                            new AdminResponse(user.getId(), user.getUsername(), roles.stream().map(Role::getName).map(Enum::name).toList(),
                                    "Error: El usuario ya tiene el rol ORGANIZADOR."));
                }
                roles.add(organizadorRole);
                break;

            default:
                return ResponseEntity.badRequest().body(
                        new AdminResponse(user.getId(), user.getUsername(), roles.stream().map(Role::getName).map(Enum::name).toList(),
                                "Error: Rol no válido. Solo se permite ADMIN u ORGANIZADOR."));
        }

        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(new AdminResponse(user.getId(), user.getUsername(), roles.stream().map(Role::getName).map(Enum::name).toList(),
                "Rol asignado exitosamente."));
    }

    /**
     * Remover roles ADMIN u ORGANIZADOR.
     */
    @PostMapping("/remove-role")
    public ResponseEntity<?> removeRole(@RequestBody AdminRequest request) {
        Optional<User> userOptional = userRepository.findById(request.getUserId());
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Usuario no encontrado.");
        }
        User user = userOptional.get();
        Set<Role> roles = user.getRoles();

        switch (request.getRoleName().toLowerCase()) {
            case "admin":
                Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException("Error: Rol ADMIN no encontrado."));
                if (!roles.contains(adminRole)) {
                    return ResponseEntity.badRequest().body(
                            new AdminResponse(user.getId(), user.getUsername(), roles.stream().map(Role::getName).map(Enum::name).toList(),
                                    "Error: El usuario no tiene el rol ADMIN."));
                }
                roles.remove(adminRole);
                break;

            case "organizador":
                Role organizadorRole = roleRepository.findByName(ERole.ROLE_ORGANIZADOR)
                        .orElseThrow(() -> new RuntimeException("Error: Rol ORGANIZADOR no encontrado."));
                if (!roles.contains(organizadorRole)) {
                    return ResponseEntity.badRequest().body(
                            new AdminResponse(user.getId(), user.getUsername(), roles.stream().map(Role::getName).map(Enum::name).toList(),
                                    "Error: El usuario no tiene el rol ORGANIZADOR."));
                }
                roles.remove(organizadorRole);
                break;

            default:
                return ResponseEntity.badRequest().body(
                        new AdminResponse(user.getId(), user.getUsername(), roles.stream().map(Role::getName).map(Enum::name).toList(),
                                "Error: Rol no válido. Solo se permite ADMIN u ORGANIZADOR."));
        }

        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(new AdminResponse(user.getId(), user.getUsername(), roles.stream().map(Role::getName).map(Enum::name).toList(),
                "Rol eliminado exitosamente."));
    }

    /**
     * Asignar rol de JURADO a un usuario y vincularlo a un evento.
     */
    @PostMapping("/assign-jury")
    public ResponseEntity<?> assignJuryRole(@RequestBody AdminJuryRequest request) {
        Optional<User> userOptional = userRepository.findById(request.getUserId());
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Usuario no encontrado.");
        }
        User user = userOptional.get();
        Optional<Event> eventOptional = eventRepository.findById(request.getEventId());
        if (eventOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Evento no encontrado.");
        }
        Event event = eventOptional.get();

        Role juradoRole = roleRepository.findByName(ERole.ROLE_JURADO)
                .orElseThrow(() -> new RuntimeException("Error: Rol JURADO no encontrado."));
        if (user.getRoles().contains(juradoRole)) {
            return ResponseEntity.badRequest().body("Error: El usuario ya tiene el rol de jurado.");
        }

        user.getRoles().add(juradoRole);
        userRepository.save(user);

        Jury jury = new Jury();
        jury.setJurado(user);
        jury.setEvent(event);
        juryRepository.save(jury);

        return ResponseEntity.ok(new AdminJuryResponse(user.getId(), user.getUsername(), event.getId(), event.getName(), "Rol de jurado asignado exitosamente."));
    }

    /**
     * Remover rol de JURADO de un usuario y eliminar su asignación a un evento.
     */
    @PostMapping("/remove-jury")
    public ResponseEntity<?> removeJuryRole(@RequestBody AdminJuryRequest request) {
        Optional<User> userOptional = userRepository.findById(request.getUserId());
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Usuario no encontrado.");
        }
        User user = userOptional.get();

        Role juradoRole = roleRepository.findByName(ERole.ROLE_JURADO)
                .orElseThrow(() -> new RuntimeException("Error: Rol JURADO no encontrado."));
        if (!user.getRoles().contains(juradoRole)) {
            return ResponseEntity.badRequest().body("Error: El usuario no tiene el rol de jurado.");
        }

        Optional<Jury> juryOptional = juryRepository.findByJurado(user);
        juryOptional.ifPresent(juryRepository::delete);

        user.getRoles().remove(juradoRole);
        userRepository.save(user);

        return ResponseEntity.ok(new AdminJuryResponse(user.getId(), user.getUsername(), null, null, "Rol de jurado eliminado exitosamente."));
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        List<BasicUserInfoResponse> users = userRepository.findAll().stream()
                .map(user -> new BasicUserInfoResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getRoles().stream().map(role -> role.getName().name()).toList()
                ))
                .toList();
        return ResponseEntity.ok(users);
    }



}
