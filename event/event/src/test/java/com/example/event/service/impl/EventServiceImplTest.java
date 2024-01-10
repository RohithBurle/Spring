package com.example.event.service.impl;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.event.exceptions.EventAlreadyExistsException;
import com.example.event.exceptions.EventNotExistsException;
import com.example.event.exceptions.NotEmptyException;
import com.example.event.exceptions.NotNullException;
import com.example.event.model.EventModel;
import com.example.event.repository.EventRepository;

class EventServiceImplTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventServiceImpl eventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateEvent_Success() {
        EventModel eventModel = new EventModel("1", "EventName", "EventDescription","12-12-24");

        when(eventRepository.existsById("1")).thenReturn(false);
        when(eventRepository.save(eventModel)).thenReturn(eventModel);

        String result = eventService.createEvent(eventModel);

        assertEquals("Success", result);
        verify(eventRepository, times(1)).save(eventModel);
    }

    @Test
    void testCreateEvent_EventAlreadyExists() {
        EventModel existingEvent = new EventModel("1", "ExistingEvent", "ExistingDescription","12-12-24");

        when(eventRepository.existsById("1")).thenReturn(true);

        assertThrows(EventAlreadyExistsException.class, () -> {
            eventService.createEvent(existingEvent);
        });

        verify(eventRepository, never()).save(existingEvent);
    }

    @Test
    void testCreateEvent_EmptyEventId() {
        EventModel eventModel = new EventModel("", "EventName", "EventDescription","12-12-24");

        assertThrows(NotEmptyException.class, () -> {
            eventService.createEvent(eventModel);
        });

        verify(eventRepository, never()).save(eventModel);
    }

    @Test
    void testCreateEvent_NullEventId() {
        EventModel eventModel = new EventModel(null, "EventName", "EventDescription","12-12-24");

        assertThrows(NotNullException.class, () -> {
            eventService.createEvent(eventModel);
        });

        verify(eventRepository, never()).save(eventModel);
    }

    @Test
    void testIsEventExists_EventExists() {
        EventModel existingEvent = new EventModel("1", "ExistingEvent", "ExistingDescription","12-12-24");

        when(eventRepository.existsById("1")).thenReturn(true);

        String result = eventService.isEventexists(existingEvent);

        assertEquals("I updated the existing data", result);
        verify(eventRepository, times(1)).save(existingEvent);
    }

    @Test
    void testIsEventExists_EventNotExists() {
        EventModel nonExistingEvent = new EventModel("2", "NonExistingEvent", "NonExistingDescription","12-12-24");

        when(eventRepository.existsById("2")).thenReturn(false);

        assertThrows(EventNotExistsException.class, () -> {
            eventService.isEventexists(nonExistingEvent);
        });

        verify(eventRepository, never()).save(nonExistingEvent);
    }

    @Test
    void testDelEvent_EventExists() {
        String eventId = "1";

        when(eventRepository.existsById(eventId)).thenReturn(true);

        String result = eventService.delEvent(eventId);

        assertEquals("Deleted Successfully", result);
        verify(eventRepository, times(1)).deleteById(eventId);
    }

    @Test
    void testDelEvent_EventNotExists() {
        String nonExistingEventId = "3";
        
        EventNotExistsException exception = assertThrows(EventNotExistsException.class,
	            () -> eventService.delEvent(nonExistingEventId));
	    assertEquals("The event with ID is not present", exception.getMessage());
    }

//correct
    @Test
    void testGetEvent_EventExists() {
        String eventId = "1";
        String nonExistingEventId = "4";
        EventModel existingEvent = new EventModel(nonExistingEventId, "ExistingEvent", "ExistingDescription","12-12-24");

        when(eventRepository.existsById(eventId)).thenReturn(true); 
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(existingEvent));

        EventModel result = eventService.getEvent(eventId);

        assertEquals(existingEvent, result);
        
        EventNotExistsException getexception = assertThrows(EventNotExistsException.class,
			    () -> eventService.getEvent(nonExistingEventId));
		System.out.println(getexception.getMessage());
    }


    @Test
    void testGetAllEvents() {
        EventModel event1 = new EventModel("1", "EventName1", "EventDescription1","12-12-24");
        EventModel event2 = new EventModel("2", "EventName2", "EventDescription2","12-12-24");

        when(eventRepository.findAll()).thenReturn(Arrays.asList(event1, event2));

        List<EventModel> result = eventService.getAllEvents();

        assertEquals(Arrays.asList(event1, event2), result);
    }
}
