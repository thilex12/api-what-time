package fr.isencaen.api_what_time.service.Model;

import fr.isencaen.api_what_time.repository.Entity.Event;

public record EventModel (
        int id,
        String name,
        String description,
        String creationDate,
        String startDate,
        String endDate,
        String location,
        boolean visibility

){

    public static EventModel of(Event event){
        return new EventModel(
                event.getId(),
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