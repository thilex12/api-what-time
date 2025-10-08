package fr.isencaen.api_what_time.controller;


import fr.isencaen.api_what_time.controller.Dto.CreateEventDto;
import fr.isencaen.api_what_time.controller.Dto.EventDto;
import fr.isencaen.api_what_time.repository.Entity.Event;
import fr.isencaen.api_what_time.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Pageable;
import java.util.List;

@RestController
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("v1/events")
    public List<EventDto> getEvents(
            Pageable pageable
    ) {
        return eventService.getEvents().stream().map(EventDto::of).toList();
    }


//    @PostMapping("v1/events")
//    @ResponseStatus(HttpStatus.CREATED)
//    public CreateEventDto createEvent(CreateEventDto event) {
//        return EventDto.of(eventService.createEvent(createEventModel.of(createEvent)));
//    }

}
