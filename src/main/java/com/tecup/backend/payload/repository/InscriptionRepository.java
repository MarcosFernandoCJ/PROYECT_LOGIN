package com.tecup.backend.payload.repository;

import com.tecup.backend.models.Event;
import com.tecup.backend.models.Inscription;
import com.tecup.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, Long> {

    Optional<Inscription> findById(Long id);
    boolean existsByUserAndEvent(User user, Event event);
    long countByEvent(Event event);

    Optional<Object> findByUser(User user);
}
