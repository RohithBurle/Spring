package com.example.event.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class EventModel {
	
	@Id
	private String eventId;
	private String eventName;
	private String eventlocation;
	private String eventDate;
	
	public EventModel() {
		
	}

	public EventModel(String eventId, String eventName, String eventlocation, String eventDate) {
		this.eventId = eventId;
		this.eventName = eventName;
		this.eventlocation = eventlocation;
		this.eventDate = eventDate;
	}
	
	
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getEventlocation() {
		return eventlocation;
	}
	public void setEventlocation(String eventlocation) {
		this.eventlocation = eventlocation;
	}
	public String getEventDate() {
		return eventDate;
	}
	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}
	
	
	

}
