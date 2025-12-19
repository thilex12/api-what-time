package fr.isencaen.api_what_time.service.Model;

import fr.isencaen.api_what_time.repository.Entity.Event;

import java.time.LocalDateTime;
import java.util.List;

public record EventModelAdmin(
        int id,
        int id_owner,
        String name,
        String description,
        LocalDateTime creationDate,
        LocalDateTime startDate,
        LocalDateTime endDate,
        LocationModel location,
        boolean visibility,
        List<TagEventModel> tags,
        List<InscriptionModel> inscriptions

) {

    public static EventModelAdmin of(Event event) {
        return new EventModelAdmin(
                event.getId(),
                event.getId_owner(),
                event.getName(),
                event.getDescription(),
                event.getCreationDate(),
                event.getStartDate(),
                event.getEndDate(),
                LocationModel.of(event.getLocation()),
                event.isVisibility(),
                event.getTagsList().stream().map(TagEventModel::of).toList(),
                event.getInscriptions().stream().map(InscriptionModel::of).toList()
//                event.getTagsList().stream().map(TagModel::of).toList()
        );
    }


}