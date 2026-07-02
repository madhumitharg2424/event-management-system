package com.eventhub.event_management.service;

import com.eventhub.event_management.entity.Event;
import com.eventhub.event_management.entity.User;
import com.eventhub.event_management.repository.EventRepository;
import com.eventhub.event_management.repository.UserRepository;
import com.eventhub.event_management.repository.RegistrationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RegistrationRepository registrationRepository;

    public Event createEvent(
            Event event,
            String email) {

        User organizer =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException("User Not Found"));

        event.setOrganizer(organizer);

        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(Long id) {

        return eventRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Event Not Found"));
    }

    @Transactional
    public void deleteEvent(
            Long id,
            String email,
            String role) {

        Event event =
                eventRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Event Not Found"));

        if (!role.equals("ADMIN") &&
                !event.getOrganizer()
                        .getEmail()
                        .equals(email)) {

            throw new RuntimeException(
                    "You can delete only your own events");
        }

        // Delete all registrations of this event first
        registrationRepository.deleteByEvent_Id(id);

        // Then delete the event
        eventRepository.deleteById(id);
    }

    public List<Event> searchEvents(String title) {

        return eventRepository
                .findByTitleContainingIgnoreCase(title);
    }

    public Event updateEvent(
            Long id,
            Event updatedEvent,
            String email,
            String role) {

        Event event =
                eventRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Event Not Found"));

        if (!role.equals("ADMIN") &&
                !event.getOrganizer()
                        .getEmail()
                        .equals(email)) {

            throw new RuntimeException(
                    "You can edit only your own events");
        }

        event.setTitle(updatedEvent.getTitle());
        event.setDescription(updatedEvent.getDescription());
        event.setVenue(updatedEvent.getVenue());
        event.setDate(updatedEvent.getDate());
        event.setTime(updatedEvent.getTime());
        event.setCapacity(updatedEvent.getCapacity());
        event.setCategory(updatedEvent.getCategory());
        event.setImageUrl(updatedEvent.getImageUrl());

        return eventRepository.save(event);
    }
}