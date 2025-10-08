package fr.isencaen.api_what_time.controller;


import fr.isencaen.api_what_time.controller.Dto.EventDto;
import fr.isencaen.api_what_time.repository.Entity.Event;
import fr.isencaen.api_what_time.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("v1/events")
    public List<EventDto> getEvents() {
        return eventService.getEvents().stream().map(EventDto::of).toList();
    }

}
