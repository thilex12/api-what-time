package fr.isencaen.api_what_time.controller.Dto;

import fr.isencaen.api_what_time.repository.Entity.Location;
import fr.isencaen.api_what_time.service.Model.UpdateEventModel;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

@ValidEventDates
public record UpdateEventDto(

        @NotBlank(message = "Le nom doit etre renseigné")
        String name,

        String description,

        @NotNull
        @FutureOrPresent
        LocalDateTime startDate,

        @NotNull
        @FutureOrPresent
        LocalDateTime endDate,

        Integer locationId,

        List<Integer> tags,

        @NotNull(message = "La visibilité doit etre renseignée")
        boolean visibility


) {
    public static UpdateEventDto of(UpdateEventModel event) {
        return new UpdateEventDto(
                event.name(),
                event.description(),
                event.startDate(),
                event.endDate(),
                event.locationId(),
                event.tags(),
                event.visibility()
        );
    }
}
