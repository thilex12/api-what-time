package fr.isencaen.api_what_time.controller.Dto;

import fr.isencaen.api_what_time.service.Model.EventModel;

public record EventDto (
        int id,
        String name,
        String description,
        String creationDate,
        String startDate,
        String endDate,
        String location,
        boolean visibility

){
    public static EventDto of(EventModel event){
        return new EventDto(
                event.id(),
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
