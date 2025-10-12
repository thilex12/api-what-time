package fr.isencaen.api_what_time.service.Model;

import fr.isencaen.api_what_time.repository.Entity.Location;

public record LocationModel(
        int id,
        String name,
        double latitude,
        double longitude,
        String description
) {
    public static LocationModel of(Location location) {
        if (location == null) {
            return null;
        }
        return new LocationModel(
                location.getId(),
                location.getName(),
                location.getLatitude(),
                location.getLongitude(),
                location.getDescription()
        );
    }
}
