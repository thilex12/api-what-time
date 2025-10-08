package fr.isencaen.api_what_time.service.Model;

import fr.isencaen.api_what_time.repository.Entity.Event;

import java.time.LocalDateTime;

public record CreateEventModel(
        String name,
        String description,
        LocalDateTime creationDate,
        LocalDateTime startDate,
        LocalDateTime endDate,
        String location,
        boolean visibility

){

    public static CreateEventModel of(Event event){
        return new CreateEventModel(
                event.getName(),
                event.getDescription(),
                event.getCreationDate(),
                event.getStartDate(),
                event.getEndDate(),
                event.getLocation(),
                event.isVisibility()
        );
    }


}