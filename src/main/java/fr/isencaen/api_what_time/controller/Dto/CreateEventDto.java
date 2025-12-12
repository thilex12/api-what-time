package fr.isencaen.api_what_time.controller.Dto;

import fr.isencaen.api_what_time.service.Model.CreateEventModel;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ValidEventDates
public record CreateEventDto(
        @NotBlank(message = "Le nom doit etre renseigné")
        String name,
        String description,

        @NotNull(message = "La date de début doit etre renseignée")
        @FutureOrPresent
        LocalDateTime startDate,

        @NotNull(message = "La date de fin doit etre renseignée")
        @FutureOrPresent
        LocalDateTime endDate,

        Integer locationId,
        boolean visibility,
        List<Integer> tags

) {
    public static CreateEventDto of(CreateEventModel event) {
        return new CreateEventDto(
                event.name(),
                event.description(),
                event.startDate(),
                event.endDate(),
                event.locationId() != null ? event.locationId() : null,
                event.visibility(),
//                new ArrayList<>(event.tags())
//                event.tags() != null ? event.tags() : new ArrayList<>()
                event.tags()
        );
    }
}
