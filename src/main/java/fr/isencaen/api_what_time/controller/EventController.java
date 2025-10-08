package fr.isencaen.api_what_time.controller;


import fr.isencaen.api_what_time.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("v1/events")
    public String getEvents() {
        return eventService.getEvents();
    }

}
