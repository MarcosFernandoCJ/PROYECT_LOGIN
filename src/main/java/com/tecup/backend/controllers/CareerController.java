package com.tecup.backend.controllers;

import com.tecup.backend.models.Career;
import com.tecup.backend.payload.repository.CareerRepository;
import com.tecup.backend.payload.repository.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/career")
public class CareerController {
    private static final Logger logger = LoggerFactory.getLogger(CareerController.class);

    @Autowired
    private CareerRepository careerRepository;

    @Autowired
    private DepartmentRepository departmentRepository;


    //Para test
    @GetMapping("/all")
    @PreAuthorize("hasRole('USER') or hasRole('ORGANIZADOR') or hasRole('ADMIN') or hasRole('JURADO')")
    public List<Career> listarCareers() {
        logger.info("Para usuarios logeados.");
        return careerRepository.findAll();
    }

    // Crear una nueva carrera
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createCareer(@RequestBody Career career) {
        logger.info("Creando una nueva carrera: {}", career.getName());

        // Validar si ya existe una carrera con el mismo nombre
        if (careerRepository.existsByName(career.getName())) {
            return ResponseEntity.badRequest().body("Error: Ya existe una carrera con el mismo nombre.");
        }

        // Validar si el departamento proporcionado existe
        if (career.getDepartment_id() == null || !departmentRepository.existsById(career.getDepartment_id().getId())) {
            return ResponseEntity.badRequest().body("Error: El departamento proporcionado no existe.");
        }

        Career newCareer = careerRepository.save(career);
        return ResponseEntity.ok("Carrera creada exitosamente: " + newCareer.getName());
    }

    // Actualizar una carrera existente
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateCareer(@PathVariable("id") Long id, @RequestBody Career updatedCareer) {
        logger.info("Actualizando la carrera con ID: {}", id);

        Optional<Career> careerOptional = careerRepository.findById(id);

        if (careerOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Carrera no encontrada.");
        }

        Career existingCareer = careerOptional.get();
        existingCareer.setName(updatedCareer.getName());

        // Validar si el departamento proporcionado existe
        if (updatedCareer.getDepartment_id() == null || !departmentRepository.existsById(updatedCareer.getDepartment_id().getId())) {
            return ResponseEntity.badRequest().body("Error: El departamento proporcionado no existe.");
        }
        existingCareer.setDepartment_id(updatedCareer.getDepartment_id());

        careerRepository.save(existingCareer);

        return ResponseEntity.ok("Carrera actualizada exitosamente: " + existingCareer.getName());
    }

    // Eliminar una carrera por ID
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCareer(@PathVariable("id") Long id) {
        logger.info("Eliminando la carrera con ID: {}", id);

        Optional<Career> careerOptional = careerRepository.findById(id);

        if (careerOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Carrera no encontrada.");
        }

        careerRepository.delete(careerOptional.get());
        return ResponseEntity.ok("Carrera eliminada exitosamente.");
    }

}
