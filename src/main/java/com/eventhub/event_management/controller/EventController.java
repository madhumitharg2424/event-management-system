package com.eventhub.event_management.controller;

import com.eventhub.event_management.entity.Event;
import com.eventhub.event_management.entity.User;
import com.eventhub.event_management.repository.RegistrationRepository;
import com.eventhub.event_management.service.EventService;
import com.eventhub.event_management.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private UserService userService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','ORGANIZER')")
    public Event createEvent(
            @RequestBody Event event,
            Principal principal) {

        return eventService.createEvent(
                event,
                principal.getName()
        );
    }

    @GetMapping
    public List<Event> getAllEvents() {

        return eventService.getAllEvents();
    }

    @GetMapping("/{id}/seats")
    public long getRegisteredCount(
            @PathVariable Long id) {

        return registrationRepository
                .countByEvent_Id(id);
    }

    @GetMapping("/{id}")
    public Event getEventById(
            @PathVariable Long id) {

        return eventService.getEventById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ORGANIZER')")
    public Event updateEvent(
            @PathVariable Long id,
            @RequestBody Event event,
            Principal principal) {

        User user =
                userService.getUserByEmail(
                        principal.getName());

        return eventService.updateEvent(
                id,
                event,
                principal.getName(),
                user.getRole()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ORGANIZER')")
    public void deleteEvent(
            @PathVariable Long id,
            Principal principal) {

        User user =
                userService.getUserByEmail(
                        principal.getName());

        eventService.deleteEvent(
                id,
                principal.getName(),
                user.getRole()
        );
    }

    @GetMapping("/search")
    public List<Event> searchEvents(
            @RequestParam String title) {

        return eventService.searchEvents(title);
    }
}