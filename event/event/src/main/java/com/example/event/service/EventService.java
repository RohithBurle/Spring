package com.example.event.service;

import java.util.List;

import com.example.event.model.EventModel;

public interface EventService {

	public String createEvent (EventModel eventmodel);
	public String updateEvent (EventModel eventmodel);
	public String delEvent (String eventId);
	public EventModel getEvent(String eventId);
	public List<EventModel> getAllEvents();
	public boolean isEventexists(String eventId);
	
}
