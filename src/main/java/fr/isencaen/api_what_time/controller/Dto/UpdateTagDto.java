package fr.isencaen.api_what_time.controller.Dto;

import fr.isencaen.api_what_time.service.Model.UpdateTagModel;

public record UpdateTagDto(
        String name
) {
    public static UpdateTagDto of(UpdateTagModel tag) {
        return new UpdateTagDto(
                tag.name()
        );
    }
}
