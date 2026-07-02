package com.eventhub.event_management.controller;

import com.eventhub.event_management.repository.EventRepository;
import com.eventhub.event_management.repository.RegistrationRepository;
import com.eventhub.event_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RegistrationRepository registrationRepository;

    @GetMapping("/stats")
    public Map<String, Object> getStats() {

        Map<String, Object> stats =
                new HashMap<>();

        stats.put(
                "totalEvents",
                eventRepository.count()
        );

        stats.put(
                "totalUsers",
                userRepository.count()
        );

        stats.put(
                "totalRegistrations",
                registrationRepository.count()
        );

        return stats;
    }
}