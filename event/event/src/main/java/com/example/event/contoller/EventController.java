package com.example.event.contoller;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.event.model.EventModel;
import com.example.event.service.EventService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/api")
public class EventController {
	
	private static final Logger logger = LoggerFactory.getLogger(EventController.class);
    


	EventService eventservice;
	
	public EventController(EventService eventservice) {
		this.eventservice = eventservice;
	}

//  pathadi
	
	//specific event
//	@GetMapping("{eventId}") //event id in this line is given by user
//	public EventModel specificEvent(@PathVariable("eventId") String eventId) //to match above and below eventID
//	{
//		return eventservice.getEvent(eventId);
//	}
//	
//	//all events
//	@GetMapping() 
//	public  List<EventModel> allEvents()
//	{
//		return eventservice.getAllEvents();
//	}
//	
//	//create
//	@PostMapping
//	public String createEvent(@RequestBody EventModel eventmodel)
//	{
//		eventservice.createEvent(eventmodel);
//		return "done sucessfully";
//	}
//	
//	//update
//	@PutMapping
//	public String updateEvent(@RequestBody EventModel eventmodel)
//	{
//		eventservice.updateEvent(eventmodel);
//		return "updated sucessfully";
//	}
//	
//	//deleted by id
//	@DeleteMapping("{eventId}")
//	public String deleteEvent(@PathVariable("eventId") String eventId)
//	{
//		eventservice.delEvent(eventId);
//		return "deleted successfully";
//	}
	
//  kothadi
	
	
	@GetMapping("{eventId}") //event id in this line is given by user
	public ResponseEntity<EventModel> specificEvent(@PathVariable("eventId") String eventId) //to match above and below eventID
	{

        EventModel event = eventservice.getEvent(eventId);
        return new ResponseEntity<>(event,HttpStatus.OK);
	}

	@GetMapping()
	public ResponseEntity<List<EventModel>> allEvents(){
		List<EventModel> allEvents = eventservice.getAllEvents();
		return new ResponseEntity<>(allEvents,HttpStatus.OK);
	}
	
	@PostMapping()
	public ResponseEntity<String> createEvent(@RequestBody EventModel eventmodel){
		try {
		logger.info("Posting");
        String status = eventservice.createEvent(eventmodel);
        return new ResponseEntity<>(status,HttpStatus.CREATED);
	} catch(Exception e) {
		logger.error("exception" + e.getMessage());
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	}
	
	@PutMapping()
	public ResponseEntity<String> updateEvent(@RequestBody EventModel eventmodel){
        String status = eventservice.updateEvent(eventmodel);
        return new ResponseEntity<>(status,HttpStatus.CREATED);
	}
	
	@PutMapping("/put")
	public ResponseEntity<String> updateEventif(@RequestBody EventModel eventmodel) {
	    if (eventservice.isEventexists(eventmodel.getEventId())) {
	        String status = eventservice.updateEvent(eventmodel);
	        return new ResponseEntity<>(status, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>("Record not found for update", HttpStatus.NOT_FOUND);
	    }
	}
	
	@DeleteMapping("{eventId}") 
	public ResponseEntity<String> delEvent(@PathVariable("eventId") String eventId) 
	{
	        String delevent = eventservice.delEvent(eventId);
	        return new ResponseEntity<>(delevent,HttpStatus.OK);
	}
}
