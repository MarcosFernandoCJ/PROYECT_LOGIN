package com.tecup.backend.payload.repository;

import com.tecup.backend.models.Jury;
import com.tecup.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JuryRepository extends JpaRepository<Jury, Long> {
    Optional<Jury> findByJurado(User user);

    Optional<Jury> findByJuradoUsername(String username);
}
