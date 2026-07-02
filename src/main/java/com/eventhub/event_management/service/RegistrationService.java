package com.eventhub.event_management.service;

import com.eventhub.event_management.dto.RegistrationRequest;
import com.eventhub.event_management.entity.Event;
import com.eventhub.event_management.entity.Registration;
import com.eventhub.event_management.entity.User;
import com.eventhub.event_management.repository.EventRepository;
import com.eventhub.event_management.repository.RegistrationRepository;
import com.eventhub.event_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    public Registration registerUser(RegistrationRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        Event event = eventRepository.findById(request.getEventId())
                .orElseThrow(() -> new RuntimeException("Event Not Found"));

        Registration registration = new Registration();

        registration.setUser(user);
        registration.setEvent(event);
        registration.setRegistrationDate(LocalDateTime.now());

        long currentRegistrations =
                registrationRepository.countByEvent_Id(event.getId());

        System.out.println("Current registrations = " + currentRegistrations);
        System.out.println("Capacity = " + event.getCapacity());
        if (
                registrationRepository
                        .existsByUser_IdAndEvent_Id(
                                user.getId(),
                                event.getId()
                        )
        ) {

            throw new RuntimeException(
                    "You have already registered for this event"
            );
        }
        if (currentRegistrations >= event.getCapacity()) {
            throw new RuntimeException("Event Full");
        }

        return registrationRepository.save(registration);
    }

    public List<Registration> getAllRegistrations() {
        return registrationRepository.findAll();
    }

    public List<Registration> getMyRegistrations(String email) {
        return registrationRepository.findByUser_Email(email);
    }

    public void deleteRegistration(Long id) {
        registrationRepository.deleteById(id);
    }
}