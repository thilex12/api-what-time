package fr.isencaen.api_what_time.service.Model;

import fr.isencaen.api_what_time.controller.Dto.UpdateEventDto;
import fr.isencaen.api_what_time.repository.Entity.Location;

import java.time.LocalDateTime;
import java.util.List;

public record UpdateEventModel(
        String name,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Integer locationId,
        List<Integer> tags,
        boolean visibility

) {

    public static UpdateEventModel of(UpdateEventDto event) {
        return new UpdateEventModel(
                event.name(),
                event.description(),
                event.startDate(),
                event.endDate(),
                event.locationId(),
                event.tags(),
                event.visibility()
        );
    }


}