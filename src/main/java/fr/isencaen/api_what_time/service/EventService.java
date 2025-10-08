package fr.isencaen.api_what_time.service;

import fr.isencaen.api_what_time.repository.Entity.Event;
import fr.isencaen.api_what_time.repository.EventRepository;
import fr.isencaen.api_what_time.service.Model.EventModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    EventRepository eventRepository;

    public List<EventModel> getEvents() {
        return eventRepository.findAll().stream().map(EventModel::of).toList();
    }


}
