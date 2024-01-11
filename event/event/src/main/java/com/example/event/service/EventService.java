package com.example.event.service;

import java.util.List;

import com.example.event.model.EventModel;

public interface EventService {

	public EventModel createEvent (EventModel eventmodel);
	public String delEvent (String eventId);
	public List<EventModel> getAllEvents();
	public String isEventexists(EventModel eventmodel);
	public EventModel getEvent(String eventId);
}
