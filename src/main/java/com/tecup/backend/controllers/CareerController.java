package com.tecup.backend.controllers;

import com.tecup.backend.models.Career;
import com.tecup.backend.payload.repository.CareerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/career")
public class CareerController {
    private static final Logger logger = LoggerFactory.getLogger(CareerController.class);

    @Autowired
    private CareerRepository careerRepository;

    @GetMapping("/all")
    @PreAuthorize("hasRole('USER') or hasRole('ORGANIZADOR') or hasRole('ADMIN') or hasRole('JURADO')")
    public List<Career> listarCareers() {
        logger.info("Para usuarios logeados.");
        return careerRepository.findAll();
    }
}
