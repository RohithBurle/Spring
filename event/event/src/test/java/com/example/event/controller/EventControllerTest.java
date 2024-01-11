package com.example.event.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.event.contoller.EventController;
import com.example.event.exceptions.EventAlreadyExistsException;
import com.example.event.exceptions.EventNotExistsException;
import com.example.event.model.EventModel;
import com.example.event.service.EventService;


class EventControllerTest {
	
	@Mock
	EventService eventservice;
	
	@InjectMocks
	EventController eventcontroller;
	
	 @BeforeEach
	    void setUp() {
	        MockitoAnnotations.openMocks(this);
	    }
	 
	//Get
	@Test
	public void test_allEvents() {
		EventModel event1 = new EventModel("1", "Event 1", "Location 1", "Date 1");
	    EventModel event2 = new EventModel("2", "Event 2", "Location 2", "Date 2");
	    
	    List<EventModel> allevents = new ArrayList<EventModel>();
	    allevents.add(event1);
	    allevents.add(event2);
	    
	    when(eventservice.getAllEvents()).thenReturn(allevents);
	    ResponseEntity<List<EventModel>> result = eventcontroller.allEvents();
	    
	    assertEquals(result.getBody().size(),allevents.size());
	    assertEquals(HttpStatus.OK,result.getStatusCode());
	}
    
	//Get By ID
	@Test
	public void test_specificEvent_ok() {
		EventModel Event = new EventModel("3","Event3","Location3","date 3");
		String eventId="3";
		
		when(eventservice.getEvent(eventId)).thenReturn(Event);
		ResponseEntity<?> specificEvent = eventcontroller.specificEvent(eventId);
		
		assertEquals(HttpStatus.OK,specificEvent.getStatusCode());
		assertEquals(Event,specificEvent.getBody());
	}
	
	@Test
	public void test_specificEvent_Exception() {
		EventModel Event = new EventModel("3","Event3","Location3","date 3");
		String eventId = "invalidEventId";

        when(eventservice.getEvent(eventId)).thenThrow(new EventNotExistsException("Event not found"));

        ResponseEntity<?> specificEvent = eventcontroller.specificEvent(eventId);
        assertEquals(HttpStatus.BAD_REQUEST, specificEvent.getStatusCode());
        assertEquals("Event not found", specificEvent.getBody());
    }
	
	//POST
	@Test
    public void test_createEvent_Success() {
        EventModel event = new EventModel("1", "Event1", "Location1", "date 1");

        when(eventservice.createEvent(event)).thenReturn(event);

        ResponseEntity<?> responseEntity = eventcontroller.createEvent(event);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(event, responseEntity.getBody());
    }

    @Test
    public void test_createEvent_Exception() {
        EventModel event = new EventModel("1", "Event1", "Location1", "date 1");

        when(eventservice.createEvent(event)).thenThrow(new EventAlreadyExistsException("Event Already Exists"));
        
        ResponseEntity<?> responseEntity = eventcontroller.createEvent(event);
        
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Event Already Exists", responseEntity.getBody());
    }

    // PUT
    @Test
    public void test_updateEventif_Success() {
        EventModel event = new EventModel("1", "Event1", "Location1", "date 1");

        when(eventservice.isEventexists(event)).thenReturn("Event updated successfully");
        ResponseEntity<String> responseEntity = eventcontroller.updateEventif(event);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Event updated successfully", responseEntity.getBody());
    }

    @Test
    public void test_updateEventif_Exception() {
        EventModel event = new EventModel("1", "Event1", "Location1", "date 1");

        when(eventservice.isEventexists(event)).thenThrow(new EventNotExistsException("Event not found"));

        ResponseEntity<String> responseEntity = eventcontroller.updateEventif(event);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Event not found", responseEntity.getBody());
    }
    
    //DELETE
    @Test
    public void test_delEvent_Success() {
        String eventId = "1";

        when(eventservice.delEvent(eventId)).thenReturn("Deleted Happily");
        

        ResponseEntity<String> responseEntity = eventcontroller.delEvent(eventId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Deleted Happily", responseEntity.getBody());
    } 

    @Test
    public void test_delEvent_Exception() {
        String eventId = "invalidEventId";
        
        when(eventservice.delEvent(eventId)).thenThrow(new EventNotExistsException("Cant delete the event"));
        ResponseEntity<String> responseEntity = eventcontroller.delEvent(eventId);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Cant delete the event", responseEntity.getBody());
    } 
}


