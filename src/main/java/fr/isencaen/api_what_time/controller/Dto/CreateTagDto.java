package fr.isencaen.api_what_time.controller.Dto;

import fr.isencaen.api_what_time.service.Model.CreateTagModel;

public record CreateTagDto(
        String name
) {
    public static CreateTagDto of(CreateTagModel tag) {
        return new CreateTagDto(
                tag.name()
        );
    }
}
