package fr.isencaen.api_what_time.controller.Dto;

import fr.isencaen.api_what_time.repository.Entity.Location;
import fr.isencaen.api_what_time.service.Model.CreateEventModel;
import fr.isencaen.api_what_time.service.Model.EventModel;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record CreateEventDto(
        int id_owner,
        @NotBlank(message = "Le nom doit etre renseigné")
        String name,

        String description,

        LocalDateTime creationDate,

        @FutureOrPresent
        @ValidEventDates
        LocalDateTime startDate,
        @FutureOrPresent
        @ValidEventDates
        LocalDateTime endDate,

        Location location,

        @NotBlank
        boolean visibility

){
    public static CreateEventDto of(CreateEventModel event){
        return new CreateEventDto(
                event.id_owner(),
                event.name(),
                event.description(),
                event.creationDate(),
                event.startDate(),
                event.endDate(),
                event.location(),
                event.visibility()

        );
    }


}
