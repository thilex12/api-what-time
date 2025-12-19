package fr.isencaen.api_what_time.controller.Dto;

import fr.isencaen.api_what_time.repository.Entity.Inscription;
import fr.isencaen.api_what_time.service.Model.EventModel;
import fr.isencaen.api_what_time.service.Model.EventModelAdmin;
import fr.isencaen.api_what_time.service.Model.TagEventModel;

import java.time.LocalDateTime;
import java.util.List;

public record EventDtoAdmin(
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
        List<Integer> tags,
        List<InscriptionDto> inscriptions,
        boolean archived

) {
    public static EventDtoAdmin of(EventModelAdmin event) {
        return new EventDtoAdmin(
                event.id(),
                event.id_owner(),
                event.name(),
                event.description(),
                event.creationDate(),
                event.startDate(),
                event.endDate(),
                event.location() == null ? 0 : event.location().id(),
                event.visibility(),
                event.tags().stream().map(TagEventModel::tagId).toList(),
                event.inscriptions().stream().map(InscriptionDto::of).toList(),
                event.archived()
        );
    }


}
