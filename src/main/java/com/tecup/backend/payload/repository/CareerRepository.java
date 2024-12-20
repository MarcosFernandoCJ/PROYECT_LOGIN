package com.tecup.backend.payload.repository;

import com.tecup.backend.models.Career;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CareerRepository extends JpaRepository<Career, Long> {

    Optional<Career> findByName(String name);


    boolean existsByName(@Size(max =100) String name);
}
