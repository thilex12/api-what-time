package fr.isencaen.api_what_time.service.Model;

import fr.isencaen.api_what_time.repository.Entity.Tag;

public record TagModel (
    int id,
    String name
) {
    public static TagModel of(Tag tag) {
        return new TagModel(
                tag.getId(),
                tag.getName()
        );
    }
}
