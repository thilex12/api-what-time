package fr.isencaen.api_what_time.controller;


import fr.isencaen.api_what_time.controller.Dto.CreateEventDto;
import fr.isencaen.api_what_time.controller.Dto.EventDto;
import fr.isencaen.api_what_time.controller.Dto.EventFilterDto;
import fr.isencaen.api_what_time.service.EventService;
import fr.isencaen.api_what_time.service.Model.CreateEventModel;
import fr.isencaen.api_what_time.service.Model.EventFilterModel;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class EventController {

    @Autowired
    private EventService eventService;

    @Cacheable(cacheNames = "events")
    @GetMapping("v1/events")
    public Page<EventDto> getEvents(
            @ParameterObject Pageable pageable,
            @ParameterObject EventFilterDto eventFilter
    ) {
        return eventService.getEvents(
                pageable,
                EventFilterModel.of(eventFilter)
        ).map(EventDto::of);
    }

    @Cacheable(cacheNames = "events")
    @GetMapping("v1/events/{id}")
    public EventDto getEventById(
            @PathVariable int id
    ) {
        return EventDto.of(eventService.getEventById(id));
    }

    @PostMapping("v1/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventDto createEvent(
            @Valid @RequestBody CreateEventDto createEventDto
    ) {
        return EventDto.of(eventService.createEvent(CreateEventModel.of(createEventDto)));
    }

    @DeleteMapping("v1/events/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvent(
            @PathVariable int id
    ) {
        eventService.deleteEvent(id);
    }
}
