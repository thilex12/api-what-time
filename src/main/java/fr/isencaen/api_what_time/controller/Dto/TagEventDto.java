package fr.isencaen.api_what_time.controller.Dto;

import fr.isencaen.api_what_time.service.Model.TagEventModel;

public record TagEventDto(
        int id,
        int tagId,
        int eventId
) {
    public static TagEventDto of(TagEventModel tagEventModel) {
        return new TagEventDto(
                tagEventModel.id(),
                tagEventModel.tagId(),
                tagEventModel.eventId()
        );
    }
}
