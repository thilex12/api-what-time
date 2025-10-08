package fr.isencaen.api_what_time.controller.Dto;

import fr.isencaen.api_what_time.service.Model.TagModel;

public record TagDto (
    int id,
    String name
) {
    public static TagDto of(TagModel tag) {
        return new TagDto(
                tag.id(),
                tag.name()
        );
    }
}
