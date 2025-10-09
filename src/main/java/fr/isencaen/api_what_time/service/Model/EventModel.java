package fr.isencaen.api_what_time.service.Model;

import fr.isencaen.api_what_time.repository.Entity.Event;

import java.time.LocalDateTime;
import java.util.List;

public record EventModel(
        int id,
        int id_owner,
        String name,
        String description,
        LocalDateTime creationDate,
        LocalDateTime startDate,
        LocalDateTime endDate,
        LocationModel location,
        boolean visibility,
        List<TagModel> tagsList

) {

    public static EventModel of(Event event) {
        return new EventModel(
                event.getId(),
                event.getId_owner(),
                event.getName(),
                event.getDescription(),
                event.getCreationDate(),
                event.getStartDate(),
                event.getEndDate(),
                LocationModel.of(event.getLocation()),
                event.isVisibility(),
                event.getTags().stream().map(TagModel::of).toList()
//                event.getTagsList().stream().map(TagModel::of).toList()
        );
    }


}