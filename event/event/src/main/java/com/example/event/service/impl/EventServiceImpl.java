package com.example.event.service.impl;

import java.util.List;
//import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.event.contoller.EventController;
import com.example.event.exceptions.EventAlreadyExistsException;
import com.example.event.exceptions.EventNotExistsException;
import com.example.event.exceptions.NotEmptyException;
import com.example.event.exceptions.NotNullException;
import com.example.event.model.EventModel;
import com.example.event.repository.EventRepository;
import com.example.event.service.EventService;

//import lombok.extern.slf4j.Slf4j;

// @Slf4j //we have use log.
@Service
public class EventServiceImpl implements EventService {

	private Logger logger = LoggerFactory.getLogger(EventController.class);
	
	EventRepository eventrepository;
 
	 
	public EventServiceImpl(EventRepository eventrepository) {
		this.eventrepository = eventrepository;
	}
	
	@Override
	public List<EventModel> getAllEvents() {
		logger.info("recieved request for Get method ");
		return eventrepository.findAll();
	}  
	   
	@Override
	public EventModel getEvent(String eventId) {
		logger.info("recieved request for Get method "); 
		if(eventrepository.existsById(eventId))
		{
			return eventrepository.findById(eventId).get();
		}
		else 
		{
			throw new EventNotExistsException("event id is no " + eventId + " not present");
		} 
	}   
  
	@Override
	public EventModel createEvent(EventModel eventmodel) {
		logger.info("recieved req from post mapping");
			if(eventrepository.existsById(eventmodel.getEventId()))
					{ 
				throw new EventAlreadyExistsException("Event already Exists");
					}  
			else {
			return eventrepository.save(eventmodel);
		}
	}
	
//	@Override
//	public EventModel createEvent(EventModel eventmodel) {
//		logger.info("recieved req from post mapping");
//		if(eventmodel.getEventId() != null)
//		{
//			if(eventmodel.getEventId().length() == 0) {
//				logger.error("raising NonEmptyException");
//				throw new NotEmptyException("Failure because event id is empty");
//			}
//			if(eventrepository.existsById(eventmodel.getEventId()))
//					{
//				throw new EventAlreadyExistsException("Event already Exists");
//					}
//			else {
//			
//			return eventrepository.save(eventmodel);
//		}}
//		else
//		{
//			logger.error("raising an NotNullException");
//			throw new NotNullException("Failure because event Id is null");
//		}
//	}
	
	@Override 
	public String isEventexists(EventModel eventmodel) {
		logger.info("recieved req from put mapping");
		if(eventrepository.existsById(eventmodel.getEventId()))
		{
			eventrepository.save(eventmodel);
			return "I updated the existing data";
			
		}
		else {
			logger.error("raising an EventNotExists Exception");
			throw new EventNotExistsException("the event is not present so i cant update the data");
		 	 
	}} 
	
	
	@Override 
	public String delEvent(String eventId) {
	    if (eventrepository.existsById(eventId)) {
	        logger.info("Request from delete mapping");
	        eventrepository.deleteById(eventId);
	        return "Deleted Successfully";
	    } else {
	    	logger.error("raising an Exception"); 
	        throw new EventNotExistsException("The event with ID is not present");
	    }
	}
}


 



	


