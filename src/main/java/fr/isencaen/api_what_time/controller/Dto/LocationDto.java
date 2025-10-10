package fr.isencaen.api_what_time.controller.Dto;

import fr.isencaen.api_what_time.service.Model.LocationModel;
import jakarta.validation.constraints.NotBlank;

public record LocationDto(
        int id,

        @NotBlank(message = "Le nom doit etre renseigné")
        String name,
        double latitude,
        double longitude,
        String description
) {
    public static LocationDto of(LocationModel location) {
        if (location == null) {
            return null;
        }
        return new LocationDto(
                location.id(),
                location.name(),
                location.latitude(),
                location.longitude(),
                location.description()
        );
    }
}
