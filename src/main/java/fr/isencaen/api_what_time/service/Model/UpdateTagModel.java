package fr.isencaen.api_what_time.service.Model;

import fr.isencaen.api_what_time.controller.Dto.UpdateTagDto;

public record UpdateTagModel(
        String name
) {
    public static UpdateTagModel of(UpdateTagDto tag) {
        return new UpdateTagModel(
                tag.name()
        );
    }
}
