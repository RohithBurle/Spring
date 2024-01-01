package com.example.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.event.model.EventModel;

public interface EventRepository extends JpaRepository<EventModel, String>{

}
