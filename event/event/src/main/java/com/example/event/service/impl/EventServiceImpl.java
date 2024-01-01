package com.example.event.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.event.exceptions.NotEmptyException;
import com.example.event.exceptions.NotNullException;
import com.example.event.model.EventModel;
import com.example.event.repository.EventRepository;
import com.example.event.service.EventService;

@Service
public class EventServiceImpl implements EventService {

	EventRepository eventrepository;
	
	
	public EventServiceImpl(EventRepository eventrepository) {
		this.eventrepository = eventrepository;
	}

	@Override
	public String createEvent(EventModel eventmodel) {
		if(eventmodel.getEventId() != null)
		{
			if(eventmodel.getEventId().length() == 0) {
				throw new NotEmptyException("Failure because event id is empty");
			}
			else {
			eventrepository.save(eventmodel);
			return "Success";
		}}
		else
		{
			throw new NotNullException("Failure because event Id is null");
		}
	}
 
	@Override
	public String updateEvent(EventModel eventmodel) {
		eventrepository.save(eventmodel);
		return "updated";
	}

	@Override
	public String delEvent(String eventId) {
		eventrepository.deleteById(eventId);
		return "deleted";
	}

	@Override
	public EventModel getEvent(String eventId) {
		return eventrepository.findById(eventId).get();
	}

	@Override
	public List<EventModel> getAllEvents() {
		return eventrepository.findAll();
	}

	@Override
	public boolean isEventexists(String eventId) {
		
		boolean existsById = eventrepository.existsById(eventId);
		return existsById;
	}

	

}
