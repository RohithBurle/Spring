package com.example.event.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.event.exceptions.EventAlreadyExistsException;
import com.example.event.exceptions.EventNotExistsException;
import com.example.event.model.EventModel;
import com.example.event.repository.EventRepository;

//@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventServiceImpl eventservice;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
      
    @Test  
    void testGetAllEvents() {

	    EventModel event1 = new EventModel("1", "Event 1", "Location 1", "Date 1");
	    EventModel event2 = new EventModel("2", "Event 2", "Location 2", "Date 2");
	    
	    List<EventModel> expectedEvents = new ArrayList<EventModel>();
	    expectedEvents.add(event1);
	    expectedEvents.add(event2);
	     
	    when(eventRepository.findAll()).thenReturn(expectedEvents);  //repo mock check

	    List<EventModel> resultEvents = eventservice.getAllEvents(); //service method tho check
	    assertEquals(expectedEvents.size(), resultEvents.size()); 
	    assertEquals(expectedEvents, resultEvents);
	}
    
    @Test 
    void testGetEvent_Success(){
        String eventId = "1";
        EventModel existingEvent = new EventModel("1", "ExistingEvent", "ExistingDescription","12-12-24");
        when(eventRepository.existsById(eventId)).thenReturn(true); 
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(existingEvent)); //Dummy Mocking Check
        EventModel result = eventservice.getEvent(eventId); //check with method name in service
        assertEquals(existingEvent, result);
	}
   
    @Test
    void testGetEvent_Exception() {
    	String nonExistingEventId = "4";
//        EventNotExistsException exception = assertThrows(EventNotExistsException.class, 
//  			    () -> eventservice.getEvent(nonExistingEventId));
    	EventModel haha = eventservice.getEvent(nonExistingEventId);
        assertEquals("event id is no " + nonExistingEventId + " not present", haha);
    }
     
    @Test
    void testCreateEvent_Success() {
        EventModel Dummy = new EventModel("1", "EventName", "EventDescription","12-12-24");
        when(eventRepository.save(Dummy)).thenReturn(Dummy); //Dummy check and return Dummy
        EventModel event = eventservice.createEvent(Dummy); //service layer check
        assertEquals(Dummy, event); 
        verify(eventRepository).save(Dummy);
    } 
    
    @Test
    void testCreateEvent_Exception() {
    EventModel existingEvent = new EventModel("5", "ExistingEvent", "ExistingDescription","12-12-24");
    when(eventRepository.existsById(existingEvent.getEventId())).thenReturn(true);
    EventAlreadyExistsException exception = assertThrows(EventAlreadyExistsException.class,
            () -> eventservice.createEvent(existingEvent));
    assertEquals("Event already Exists", exception.getMessage()); 
    }
 
//    @Test 
//    void testCreateEvent_EmptyEventId() {
//        EventModel emptyevent = new EventModel("", "EventName", "EventDescription","12-12-24");
//        NotEmptyException throws1 = assertThrows(NotEmptyException.class,
//	            () -> eventservice.createEvent(emptyevent));
//        assertEquals("Failure because event id is empty", throws1.getMessage());
//    } 
// 
//    @Test 
//    void testCreateEvent_NullEventId() {
//        EventModel nullid = new EventModel(null, "EventName", "EventDescription","12-12-24");
//        NotNullException throws2 = assertThrows(NotNullException.class,
//	            () -> eventservice.createEvent(nullid));
//        assertEquals("Failure because event Id is null", throws2.getMessage());
//    }

    @Test
    void testisEventexits_success() { 
    	EventModel existingEvent = new EventModel("1", "ExistingEvent", "ExistingDescription","12-12-24");
    	when(eventRepository.existsById(existingEvent.getEventId())).thenReturn(true);
     	
    	String result = eventservice.isEventexists(existingEvent); //checking from service
    	
    	assertEquals("I updated the existing data", result);
        verify(eventRepository).save(existingEvent);  
    }
    
    @Test
    void testisEventexists_Exception() {
    	 EventModel nonExistingEvent = new EventModel("2", "NonExistingEvent", "NonExistingDescription","12-12-24");
         when(eventRepository.existsById("2")).thenReturn(false);
         EventNotExistsException updateexception = assertThrows(EventNotExistsException.class,
                 () -> eventservice.isEventexists(nonExistingEvent));
         assertEquals("the event is not present so i cant update the data", updateexception.getMessage());
    }

    @Test 
    void testDelEvent_Success() {
        String eventId = "1"; 
        when(eventRepository.existsById(eventId)).thenReturn(true);
        String result = eventservice.delEvent(eventId);
        assertEquals("Deleted Successfully", result);
        verify(eventRepository).deleteById(eventId); 
    }
    
    @Test
    void testDelEvent_Exception() {
        String nonExistingEventId = "3"; 
    EventNotExistsException exception = assertThrows(EventNotExistsException.class,
            () -> eventservice.delEvent(nonExistingEventId)); 
    assertEquals("The event with ID is not present", exception.getMessage());
    }
  }
