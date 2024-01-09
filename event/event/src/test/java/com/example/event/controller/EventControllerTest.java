package com.example.event.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.event.contoller.EventController;
import com.example.event.model.EventModel;
import com.example.event.service.EventService;

class EventControllerTest {

    @Mock
    private EventService eventService;

    @InjectMocks
    private EventController eventController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAllEvents() {
        // Arrange
        List<EventModel> mockEvents = Arrays.asList(new EventModel(), new EventModel());
        when(eventService.getAllEvents()).thenReturn(mockEvents);

        // Act
        ResponseEntity<List<EventModel>> response = eventController.allEvents();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockEvents, response.getBody());
        verify(eventService, times(1)).getAllEvents();
    }

    @Test
    void testSpecificEvent() {
        // Arrange
        String eventId = "123";
        EventModel mockEvent = new EventModel();
        when(eventService.getEvent(eventId)).thenReturn(mockEvent);

        // Act
        ResponseEntity<?> response = eventController.specificEvent(eventId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockEvent, response.getBody());
        verify(eventService, times(1)).getEvent(eventId);
    }

    @Test
    void testSpecificEventWithException() {
        // Arrange
        String eventId = "123";
        when(eventService.getEvent(eventId)).thenThrow(new RuntimeException("Test Exception"));

        // Act
        ResponseEntity<?> response = eventController.specificEvent(eventId);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Test Exception", response.getBody());
        verify(eventService, times(1)).getEvent(eventId);
    }

    @Test
    void testCreateEvent() {
        // Arrange
        EventModel mockEvent = new EventModel();
        when(eventService.createEvent(mockEvent)).thenReturn("Created Successfully");

        // Act
        ResponseEntity<?> response = eventController.createEvent(mockEvent);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Created Successfully", response.getBody());
        verify(eventService, times(1)).createEvent(mockEvent);
    }

    @Test
    void testCreateEventWithException() {
        // Arrange
        EventModel mockEvent = new EventModel();
        when(eventService.createEvent(mockEvent)).thenThrow(new RuntimeException("Event Already Exists"));

        // Act
        ResponseEntity<?> response = eventController.createEvent(mockEvent);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Event Already Exists", response.getBody());
        verify(eventService, times(1)).getEvent(mockEvent.getEventId());
    }

    @Test
    void testUpdateEventIf() {
        // Arrange
        EventModel mockEvent = new EventModel();
        when(eventService.isEventexists(mockEvent)).thenReturn("Updated Successfully");

        // Act
        ResponseEntity<String> response = eventController.updateEventif(mockEvent);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated Successfully", response.getBody());
        verify(eventService, times(1)).isEventexists(mockEvent);
    }

    @Test
    void testUpdateEventIfWithException() {
        // Arrange
        EventModel mockEvent = new EventModel();
        when(eventService.isEventexists(mockEvent)).thenThrow(new RuntimeException("Event Not Found"));

        // Act
        ResponseEntity<String> response = eventController.updateEventif(mockEvent);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Event Not Found", response.getBody());
        verify(eventService, times(1)).isEventexists(mockEvent);
    }

    @Test
    void testDelEvent() {
        // Arrange
        String eventId = "123";

        // Act
        ResponseEntity<String> response = eventController.delEvent(eventId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Deleted Happily", response.getBody());
        verify(eventService, times(1)).delEvent(eventId);
    }

    @Test
    void testDelEventWithException() {
        // Arrange
        String eventId = "123";
        doThrow(new RuntimeException("Event Deletion Failed")).when(eventService).delEvent(eventId);

        // Act
        ResponseEntity<String> response = eventController.delEvent(eventId);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Event Deletion Failed", response.getBody());
        verify(eventService, times(1)).delEvent(eventId);
    }
}
