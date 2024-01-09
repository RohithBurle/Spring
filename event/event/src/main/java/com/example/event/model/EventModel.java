package com.example.event.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;

@Data
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
}

