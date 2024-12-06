package com.tecup.backend.payload.repository;

import com.tecup.backend.models.Department;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    boolean existsByName(@Size(max = 100) String name);
}
