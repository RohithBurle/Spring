package com.example.event.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.event.model.EventModel;


@DataJpaTest
public class EventRepositoryTest {

	@Autowired
	private EventRepository eventrepository;
	
	EventModel eventmodel;
    
    @BeforeEach
    void setUp() {
		eventmodel = new EventModel("123","Donation","Delhi","12-55-66");
    	eventrepository.save(eventmodel);
    }
    
    //TestCase for Success
    @Test
    void testEventByName_Found() {
    	List<EventModel> eventmodellist = eventrepository.findByEventName("Donation");
    	assertThat(eventmodellist.get(0).getEventId()).isEqualTo(eventmodel.getEventId());
    	assertThat(eventmodellist.get(0).getEventlocation())
    	              .isEqualTo(eventmodel.getEventlocation());  	
    }
    
    //TestCase for Failure
    @Test
    void testEventByName_NotFound() {
    	List<EventModel> eventmodellist = eventrepository.findByEventName("Happy");
    	assertThat(eventmodellist.isEmpty()).isTrue();    }
}
