package fr.isencaen.api_what_time.service.Model;

import fr.isencaen.api_what_time.controller.Dto.CreateEventDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public record CreateEventModel(
        String name,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Integer locationId,
        boolean visibility,
        List<Integer> tags
) {

    public static CreateEventModel of(CreateEventDto event) {
        return new CreateEventModel(
                event.name(),
                event.description(),
                event.startDate(),
                event.endDate(),
                event.locationId(),
                event.visibility(),
                event.tags()
//                event.tags() != null ? event.tags() : new ArrayList<>()
        );
    }

}