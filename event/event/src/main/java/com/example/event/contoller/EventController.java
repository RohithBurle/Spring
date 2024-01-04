package com.example.event.contoller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.event.model.EventModel;
import com.example.event.service.EventService;

@Controller
@RequestMapping("/api")
public class EventController {

	private Logger logger = LoggerFactory.getLogger(EventController.class);

	EventService eventservice;

	public EventController(EventService eventservice) {
		this.eventservice = eventservice;
	}

//	//specific event
//	@GetMapping("{eventId}") //event id in this line is given by user
//	public EventModel specificEvent(@PathVariable("eventId") String eventId) //to match above and below eventID
//	{
//		return eventservice.getEvent(eventId);
//	}
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
//	@PutMapping()
//	public String updateEvent(@RequestBody EventModel eventmodel)
//	{
//		try {
//			eventservice.Done(eventmodel);
//		    return "updated sucessfully";
//		} catch (EventNotExistsException e) {
//			return e.getMessage();
//		}
//	}
//	
//	//deleted by id
//	@DeleteMapping("{eventId}")
//	public String deleteEvent(@PathVariable("eventId") String eventId)
//	{
//		eventservice.delEvent(eventId);
//		return "deleted successfully";
//	}
//}

	@GetMapping()
	public ResponseEntity<List<EventModel>> allEvents() {
		logger.info("Retrive all events");
		List<EventModel> allEvents = eventservice.getAllEvents();
		return new ResponseEntity<>(allEvents, HttpStatus.OK);
	}

	@GetMapping("{eventId}") // event id in this line is given by user
	public ResponseEntity<?> specificEvent(@PathVariable("eventId") String eventId) // to match above and below																						// eventID
	{
		try {
		logger.info("specific event");
		EventModel event = eventservice.getEvent(eventId);
		return new ResponseEntity<>(event, HttpStatus.OK);
		} catch(Exception e)
		{
			logger.error("exception" + e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping()
	public ResponseEntity<?> createEvent(@RequestBody EventModel eventmodel) {
		try {
			logger.info("Posting");
			String status = eventservice.createEvent(eventmodel); // object comes here
			return new ResponseEntity<>(status, HttpStatus.CREATED);
		} catch (Exception e) {
//			if(e.getMessage().endsWith("Exists"))
//			{
//				logger.error("Returning the data");
//				EventModel event = eventservice.getEvent(eventmodel.getEventId());
//				return new ResponseEntity<>(event, HttpStatus.OK);
//			}
			logger.error("exception" + e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping()
	public ResponseEntity<String> updateEventif(@RequestBody EventModel eventmodel) {
		try {
			logger.info("Updating if only present");
			String status = eventservice.isEventexists(eventmodel);
			return new ResponseEntity<>(status, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("EventNotExistsException: " + e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("{eventId}")
	public ResponseEntity<String> delEvent(@PathVariable("eventId") String eventId) {
		try {
		logger.info("Deleting the specifc event");
		String delevent = (String) eventservice.delEvent(eventId);
		return new ResponseEntity<>(delevent, HttpStatus.OK);
		} catch(Exception e)
		{
			logger.error("Cant delete the event" + e.getMessage());
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(path = "**")
    public ResponseEntity<String> handleInvalidEndpoint() {
        logger.error("Invalid endpoint requested");
        return new ResponseEntity<>("Invalid endpoint requested", HttpStatus.NOT_FOUND);
}
}
