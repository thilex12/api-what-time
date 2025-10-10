package fr.isencaen.api_what_time.controller.Dto;

import fr.isencaen.api_what_time.service.Model.EventModel;

import java.time.LocalDateTime;
import java.util.List;

public record EventDto(
        int id,
        int id_owner,
        String name,
        String description,
        LocalDateTime creationDate,
        LocalDateTime startDate,
        LocalDateTime endDate,
        LocationDto location,
        boolean visibility,
        List<TagEventDto> tags

) {
    public static EventDto of(EventModel event) {
        return new EventDto(
                event.id(),
                event.id_owner(),
                event.name(),
                event.description(),
                event.creationDate(),
                event.startDate(),
                event.endDate(),
                LocationDto.of(event.location()),
                event.visibility(),
                event.tags().stream().map(TagEventDto::of).toList()

        );
    }


}
