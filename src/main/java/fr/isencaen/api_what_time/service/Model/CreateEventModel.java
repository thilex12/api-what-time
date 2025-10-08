package fr.isencaen.api_what_time.service.Model;

import fr.isencaen.api_what_time.controller.Dto.CreateEventDto;
import fr.isencaen.api_what_time.repository.Entity.Event;

import java.time.LocalDateTime;

public record CreateEventModel(
        int id_owner,
        String name,
        String description,
        LocalDateTime creationDate,
        LocalDateTime startDate,
        LocalDateTime endDate,
        String location,
        boolean visibility

){

    public static CreateEventModel of(CreateEventDto event){
        return new CreateEventModel(
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