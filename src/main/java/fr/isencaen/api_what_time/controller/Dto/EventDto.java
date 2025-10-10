package fr.isencaen.api_what_time.controller.Dto;

import fr.isencaen.api_what_time.service.Model.EventModel;
import fr.isencaen.api_what_time.service.Model.TagEventModel;

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
        int location,
        boolean visibility,
//        List<TagEventDto> tags
        List<Integer> tags
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
//                LocationDto.of(event.location()),
                event.location() == null ? 0 : event.location().id(),
                event.visibility(),
//                event.tags().stream().map(TagEventDto::of).toList()
//                event.tags().stream().map(tagEventModel -> new TagDto(tagEventModel.tagId(), ).toList()
                event.tags().stream().map(TagEventModel::tagId).toList()

        );
    }


}
