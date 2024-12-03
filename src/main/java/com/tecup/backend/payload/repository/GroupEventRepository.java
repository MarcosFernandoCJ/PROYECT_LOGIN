package com.tecup.backend.payload.repository;

import com.tecup.backend.models.Department;
import com.tecup.backend.models.Event;
import com.tecup.backend.models.GroupEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupEventRepository extends JpaRepository<GroupEvent, Long> {
    Optional<GroupEvent> findByEventAndDepartment(Event event, Department department);
}
