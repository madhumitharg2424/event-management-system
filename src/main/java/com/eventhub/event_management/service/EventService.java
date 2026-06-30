package com.eventhub.event_management.service;

import com.eventhub.event_management.entity.Event;
import com.eventhub.event_management.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event Not Found"));
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
    public List<Event> searchEvents(String title) {
        return eventRepository.findByTitleContainingIgnoreCase(title);
    }
    public Event updateEvent(Long id, Event updatedEvent) {

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event Not Found"));

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