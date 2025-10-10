package fr.isencaen.api_what_time.service.Model;

import fr.isencaen.api_what_time.controller.Dto.CreateEventDto;
import fr.isencaen.api_what_time.repository.Entity.Location;

import java.time.LocalDateTime;
import java.util.List;

public record CreateEventModel(
//        int id_owner,
        String name,
        String description,
//        LocalDateTime creationDate,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Location location,
        boolean visibility,
        List<Integer> tagsList
//        boolean isArchived

) {

    public static CreateEventModel of(CreateEventDto event) {
        return new CreateEventModel(
////                event.id_owner(),
//                event.getName(),
//                event.getDescription(),
////                event.creationDate(),
//                event.getStartDate(),
//                event.getEndDate(),
//                event.getLocation(),
//                event.isVisibility(),
//                event.getTags().stream().map(TagModel::of).toList()
////                event.isArchived(),

                event.name(),
                event.description(),
//                event.creationDate(),
                event.startDate(),
                event.endDate(),
                event.location() != null ? event.location() : null,
                event.visibility(),
                event.tagsList() != null ? event.tagsList() : List.of()
//                event.tagsList() != null ? event.tagsList().stream().map(TagModelForCreateEvent::of).toList() : List.of()//                event.isArchived(),

        );
    }


}