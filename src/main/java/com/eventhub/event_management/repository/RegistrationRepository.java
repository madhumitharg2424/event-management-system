package com.eventhub.event_management.repository;

import com.eventhub.event_management.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistrationRepository
        extends JpaRepository<Registration, Long> {

    long countByEvent_Id(Long eventId);
    long countByEventId(Long eventId);
    List<Registration> findByUser_Email(String email);
    long count();

    boolean existsByUser_IdAndEvent_Id(
            Long userId,
            Long eventId
    );
    void deleteByEvent_Id(Long eventId);
}