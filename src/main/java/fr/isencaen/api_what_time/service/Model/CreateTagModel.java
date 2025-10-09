package fr.isencaen.api_what_time.service.Model;

import fr.isencaen.api_what_time.controller.Dto.CreateTagDto;

public record CreateTagModel(
        String name
) {
    public static CreateTagModel of(CreateTagDto tag) {
        return new CreateTagModel(
                tag.name()
        );
    }
}
