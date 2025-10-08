package fr.isencaen.api_what_time.service.Model;

import fr.isencaen.api_what_time.controller.Dto.EventFilterDto;

import java.util.Optional;

public record EventFilterModel(
        Optional<String> name
){
    public static EventFilterModel of(EventFilterDto eventFilterDto) {
        return new EventFilterModel(
                eventFilterDto.name()
        );
    }

}
