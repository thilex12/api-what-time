package fr.isencaen.api_what_time.controller;


import fr.isencaen.api_what_time.controller.Dto.CreateEventDto;
import fr.isencaen.api_what_time.controller.Dto.EventDto;
import fr.isencaen.api_what_time.controller.Dto.EventFilterDto;
import fr.isencaen.api_what_time.repository.Entity.Event;
import fr.isencaen.api_what_time.service.EventService;
import fr.isencaen.api_what_time.service.Model.CreateEventModel;
import fr.isencaen.api_what_time.service.Model.EventFilterModel;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.EventFilter;
import java.awt.print.Pageable;
import java.util.List;

@RestController
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("v1/events")
    public List<EventDto> getEvents(
            @ParameterObject Pageable pageable,
            @ParameterObject EventFilterDto eventFilter
    ) {
        return eventService.getEvents(
                pageable,
                EventFilterModel.of(eventFilter)
        ).stream().map(EventDto::of).toList();
    }


    @PostMapping("v1/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventDto createEvent(
            @RequestBody CreateEventDto createEventDto
    ) {

        return EventDto.of(eventService.createEvent(CreateEventModel.of(createEventDto)));
    }

}
