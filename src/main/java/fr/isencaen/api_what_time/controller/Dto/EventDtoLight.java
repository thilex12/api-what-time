package fr.isencaen.api_what_time.controller.Dto;

import fr.isencaen.api_what_time.service.Model.EventModel;

import java.time.LocalDateTime;

public record EventDtoLight(
        int id,
        int id_owner,
        String name,
        String description,
        LocalDateTime creationDate,
        LocalDateTime startDate,
        LocalDateTime endDate,
        boolean visibility
) {
    public static EventDtoLight of(EventModel eventModel) {
        return new EventDtoLight(
                eventModel.id(),
                eventModel.id_owner(),
                eventModel.name(),
                eventModel.description(),
                eventModel.creationDate(),
                eventModel.startDate(),
                eventModel.endDate(),
                eventModel.visibility()
        );
    }
}

