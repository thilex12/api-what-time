package fr.isencaen.api_what_time.controller.Dto;

import fr.isencaen.api_what_time.service.Model.CreateEventModel;
import fr.isencaen.api_what_time.service.Model.EventModel;

import java.time.LocalDateTime;

public record CreateEventDto(
        String name,
        String description,
        LocalDateTime creationDate,
        LocalDateTime startDate,
        LocalDateTime endDate,
        String location,
        boolean visibility

){
    public static CreateEventDto of(CreateEventModel event){
        return new CreateEventDto(
                event.name(),
                event.description(),
                event.creationDate(),
                event.startDate(),
                event.endDate(),
                event.location(),
                event.visibility()

        );
    }


}
