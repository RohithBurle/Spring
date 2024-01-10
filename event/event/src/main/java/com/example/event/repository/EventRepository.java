package com.example.event.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.event.model.EventModel;

public interface EventRepository extends JpaRepository<EventModel, String>{
}
