package fr.isencaen.api_what_time.service.Model;

import fr.isencaen.api_what_time.controller.Dto.UpdateEventDto;
import fr.isencaen.api_what_time.repository.Entity.Location;

import java.time.LocalDateTime;

public record UpdateEventModel(
        String name,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Location location,
        boolean visibility

) {

    public static UpdateEventModel of(UpdateEventDto event) {
        return new UpdateEventModel(
                event.name(),
                event.description(),
                event.startDate(),
                event.endDate(),
                event.location(),
                event.visibility()
        );
    }


}