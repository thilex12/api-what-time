package fr.isencaen.api_what_time.controller.Dto;

import fr.isencaen.api_what_time.repository.Entity.Location;
import fr.isencaen.api_what_time.service.Model.CreateEventModel;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@ValidEventDates
public record CreateEventDto(
        int id_owner,
        @NotBlank(message = "Le nom doit etre renseigné")
        String name,

        String description,

        LocalDateTime creationDate,

        @NotBlank(message = "La date de début doit etre renseignée")
        @FutureOrPresent
        LocalDateTime startDate,

        @NotBlank(message = "La date de fin doit etre renseignée")
        @FutureOrPresent
        LocalDateTime endDate,

        Location location,

        @NotBlank(message = "La visibilité doit etre renseignée")
        boolean visibility,


        boolean isArchived

) {
    public static CreateEventDto of(CreateEventModel event) {
        return new CreateEventDto(
                event.id_owner(),
                event.name(),
                event.description(),
                event.creationDate(),
                event.startDate(),
                event.endDate(),
                event.location(),
                event.visibility(),
                event.isArchived()

        );
    }


}
