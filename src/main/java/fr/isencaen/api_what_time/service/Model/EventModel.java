package fr.isencaen.api_what_time.service.Model;

import fr.isencaen.api_what_time.repository.Entity.Event;

import java.time.LocalDateTime;

public record EventModel (
        int id,
        int id_owner,
        String name,
        String description,
        LocalDateTime creationDate,
        LocalDateTime startDate,
        LocalDateTime endDate,
        int location,
        boolean visibility

){

    public static EventModel of(Event event){
        return new EventModel(
                event.getId(),
                event.getId_owner(),
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