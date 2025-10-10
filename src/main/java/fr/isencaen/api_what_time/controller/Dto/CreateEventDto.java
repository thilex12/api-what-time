package fr.isencaen.api_what_time.controller.Dto;

import fr.isencaen.api_what_time.repository.Entity.Location;
import fr.isencaen.api_what_time.service.Model.CreateEventModel;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

@ValidEventDates
public record CreateEventDto(
//        int id_owner,
        @NotBlank(message = "Le nom doit etre renseigné")
        String name,

        String description,

//        LocalDateTime creationDate,

        @NotNull(message = "La date de début doit etre renseignée")
        @FutureOrPresent
        LocalDateTime startDate,

        @NotNull(message = "La date de fin doit etre renseignée")
        @FutureOrPresent
        LocalDateTime endDate,

        Location location,

//        @NotBlank(message = "La visibilité doit etre renseignée")
        boolean visibility,
        List<Integer> tagsList


//        boolean isArchived

) {
    public static CreateEventDto of(CreateEventModel event) {
        return new CreateEventDto(
//                event.id_owner(),
                event.name(),
                event.description(),
//                event.creationDate(),
                event.startDate(),
                event.endDate(),
                event.location(),
                event.visibility(),
                event.tagsList()
//                event.isArchived()

        );
    }


}
