package com.eventhub.event_management.controller;

import com.eventhub.event_management.dto.RegistrationRequest;
import com.eventhub.event_management.entity.Registration;
import com.eventhub.event_management.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registrations")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping
    public Registration registerUser(
            @RequestBody RegistrationRequest request) {

        return registrationService.registerUser(request);
    }

    @GetMapping
    public List<Registration> getAllRegistrations() {
        return registrationService.getAllRegistrations();
    }

    @DeleteMapping("/{id}")
    public String deleteRegistration(
            @PathVariable Long id) {

        registrationService.deleteRegistration(id);

        return "Registration Deleted Successfully";
    }
}