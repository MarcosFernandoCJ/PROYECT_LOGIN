package com.tecup.backend.controllers;

import com.tecup.backend.models.Career;
import com.tecup.backend.models.Department;
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
@RequestMapping("/api/department")
public class DepartmentController {
    private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    private DepartmentRepository departmentRepository;

    //para test
    @GetMapping("/all")
    @PreAuthorize("hasRole('USER') or hasRole('ORGANIZADOR') or hasRole('ADMIN') or hasRole('JURADO')")
    public List<Department> listarDepartments() {
        logger.info("Para usuarios logeados.");
        return departmentRepository.findAll();
    }

    // Crear un nuevo departamento
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createDepartment(@RequestBody Department department) {
        logger.info("Creando un nuevo departamento: {}", department.getName());

        // Validar si ya existe un departamento con el mismo nombre
        if (departmentRepository.existsByName(department.getName())) {
            return ResponseEntity.badRequest().body("Error: Ya existe un departamento con el mismo nombre.");
        }

        Department newDepartment = departmentRepository.save(department);
        return ResponseEntity.ok("Departamento creado exitosamente: " + newDepartment.getName());
    }

    // Actualizar un departamento existente
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateDepartment(@PathVariable("id") Long id, @RequestBody Department updatedDepartment) {
        logger.info("Actualizando el departamento con ID: {}", id);

        Optional<Department> departmentOptional = departmentRepository.findById(id);

        if (departmentOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Departamento no encontrado.");
        }

        Department existingDepartment = departmentOptional.get();
        existingDepartment.setName(updatedDepartment.getName());
        departmentRepository.save(existingDepartment);

        return ResponseEntity.ok("Departamento actualizado exitosamente: " + existingDepartment.getName());
    }

    // Eliminar un departamento por ID
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteDepartment(@PathVariable("id") Long id) {
        logger.info("Eliminando el departamento con ID: {}", id);

        Optional<Department> departmentOptional = departmentRepository.findById(id);

        if (departmentOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Departamento no encontrado.");
        }

        departmentRepository.delete(departmentOptional.get());
        return ResponseEntity.ok("Departamento eliminado exitosamente.");
    }
}


