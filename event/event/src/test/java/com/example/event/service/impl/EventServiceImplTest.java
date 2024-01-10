package com.example.event.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.event.exceptions.EventNotExistsException;
import com.example.event.model.EventModel;
import com.example.event.repository.EventRepository;
import com.example.event.service.EventService;

public class EventServiceImplTest {

	@Mock
	private EventRepository eventrepository;
	
	private EventService eventservice;
	AutoCloseable autocloseable;
	EventModel eventmodel;
	
	@BeforeEach
	void setUp() {
		autocloseable = MockitoAnnotations.openMocks(this);
		eventservice = new EventServiceImpl(eventrepository);
		eventmodel = new EventModel("1000","Donation","Delhi","12-55-66");
	}
	
	@AfterEach
	void tearDown() throws Exception {
		autocloseable.close();
	}
	
	@Test //create
	void testCreateEvent() {
		mock(EventModel.class);
		mock(EventRepository.class);
		
		when(eventrepository.save(eventmodel)).thenReturn(eventmodel);
		assertThat(eventservice.createEvent(eventmodel)).isEqualTo("Success");
	}
	  
	  @Test
	    public void testIsEventExists() { 
	        EventModel Dummy = new EventModel();
	        Dummy.setEventId("1234"); //change id as per case
	        //when id is not present
	        when(eventrepository.existsById(Dummy.getEventId())).thenReturn(false);
	        EventNotExistsException update = assertThrows(EventNotExistsException.class,
	                () -> eventservice.isEventexists(Dummy));
	        assertEquals("the event is not present so i cant update the data", update.getMessage());
	        // when id is present
	        when(eventrepository.existsById(Dummy.getEventId())).thenReturn(true);
	        String resultExisting = eventservice.isEventexists(Dummy);
	        assertEquals("I updated the existing data", resultExisting);
	        verify(eventrepository).save(Dummy);
	    }
	
	@Test
	void testdelEvent() {
	    EventModel delDummy = new EventModel("1000","Donation","Delhi","12-55-66");
	    delDummy.setEventId("10000");
	    // when id is present
	    when(eventrepository.existsById(delDummy.getEventId())).thenReturn(true);
	    String result = eventservice.delEvent(delDummy.getEventId());
	    assertEquals("Deleted Successfully", result);
	    verify(eventrepository).deleteById(delDummy.getEventId());
	    // when id is not present
	    when(eventrepository.existsById(delDummy.getEventId())).thenReturn(false);
	    EventNotExistsException exception = assertThrows(EventNotExistsException.class,
	            () -> eventservice.delEvent(delDummy.getEventId()));
	    assertEquals("The event with ID is not present", exception.getMessage());
	}

	@Test
	void testgetEvent() {
		EventModel getDummy = new EventModel("1000","Donation","Delhi","12-55-66");
		getDummy.setEventId("100");
		when(eventrepository.existsById(getDummy.getEventId())).thenReturn(true);
		when(eventrepository.findById(getDummy.getEventId())).thenReturn(Optional.of(getDummy));
		EventModel result = eventservice.getEvent(getDummy.getEventId());
		assertEquals(getDummy,result);
		when(eventrepository.existsById(getDummy.getEventId())).thenReturn(false);
		EventNotExistsException getexception = assertThrows(EventNotExistsException.class,
			    () -> eventservice.getEvent(getDummy.getEventId()));
		 assertEquals("event id is no " + getDummy.getEventId() + " not present", getexception.getMessage());
	}
	@Test
	public void testGetAllEvents() {

	    EventModel event1 = new EventModel("1", "Event 1", "Location 1", "Date 1");
	    EventModel event2 = new EventModel("2", "Event 2", "Location 2", "Date 2");
	    List<EventModel> expectedEvents = new ArrayList<EventModel>();
	    expectedEvents.add(event1);
	    expectedEvents.add(event2);
	    
	    when(eventrepository.findAll()).thenReturn(expectedEvents);

	    List<EventModel> resultEvents = eventservice.getAllEvents();
	    assertEquals(expectedEvents.size(), resultEvents.size());
	    assertEquals(expectedEvents, resultEvents);
	}

}

