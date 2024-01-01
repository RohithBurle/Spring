package com.example.restapi.service;

import com.example.restapi.model.Event;
import com.example.restapi.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(Long id) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        return optionalEvent.orElse(null);
    }

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event updateEvent(Long id, Event event) {
        if (eventRepository.existsById(id)) {
            event.setEventId(id); // Ensure the ID is set for the update
            return eventRepository.save(event);
        }
        return null;
    }

    public boolean deleteEvent(Long id) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Event partiallyUpdateEvent(Long id, Event updatedEventInfo) {
        Event existingEvent = eventRepository.findById(id).orElse(null);
        if (existingEvent == null) {
            return null;
        }
        if (updatedEventInfo.getEventName() != null) {
            existingEvent.setEventName(updatedEventInfo.getEventName());
        }
        if (updatedEventInfo.getEventLocation() != null) {
            existingEvent.setEventLocation(updatedEventInfo.getEventLocation());
        }
        if (updatedEventInfo.getEventDate() != null) {
            existingEvent.setEventDate(updatedEventInfo.getEventDate());
        }
        return eventRepository.save(existingEvent);
    }
}
