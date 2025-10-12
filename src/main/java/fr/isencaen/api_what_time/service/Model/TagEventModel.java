package fr.isencaen.api_what_time.service.Model;

import fr.isencaen.api_what_time.repository.Entity.TagEvent;

public record TagEventModel(
        int id,
        int tagId,
        int eventId
) {
    public static TagEventModel of(TagEvent tagEvent) {
        return new TagEventModel(
                tagEvent.getId(),
                tagEvent.getTag().getId(),
                tagEvent.getEvent().getId()
        );
    }
}
