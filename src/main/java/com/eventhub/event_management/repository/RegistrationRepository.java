package com.eventhub.event_management.repository;

import com.eventhub.event_management.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository
        extends JpaRepository<Registration, Long> {

    long countByEvent_Id(Long eventId);
}