package fr.isencaen.api_what_time.service.Model;

import fr.isencaen.api_what_time.controller.Dto.TagDto;

public record TagModelForCreateEvent(
        int id,
        String name
) {
    public static TagModel of(TagDto tag) {
        return new TagModel(
                tag.id(),
                tag.name()
        );
    }
}
