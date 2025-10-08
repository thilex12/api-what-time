package fr.isencaen.api_what_time.controller.Dto;

import fr.isencaen.api_what_time.service.Model.CreateEventModel;
import fr.isencaen.api_what_time.service.Model.EventModel;

import java.time.LocalDateTime;

public record CreateEventDto(
        int id_owner,
        String name,
        String description,
        LocalDateTime creationDate,
        LocalDateTime startDate,
        LocalDateTime endDate,
        int location,
        boolean visibility

){
    public static CreateEventDto of(CreateEventModel event){
        return new CreateEventDto(
                event.id_owner(),
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
