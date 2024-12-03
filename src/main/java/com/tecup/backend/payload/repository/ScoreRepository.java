package com.tecup.backend.payload.repository;

import com.tecup.backend.models.GroupEvent;
import com.tecup.backend.models.Jury;
import com.tecup.backend.models.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
    Optional<Score> findByJuryAndGroupEvent(Jury jury, GroupEvent group);
}
