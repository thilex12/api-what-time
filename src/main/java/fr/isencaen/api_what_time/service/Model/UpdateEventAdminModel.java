package fr.isencaen.api_what_time.service.Model;

import fr.isencaen.api_what_time.controller.Dto.UpdateEventAdminDto;
import fr.isencaen.api_what_time.controller.Dto.UpdateEventDto;

import java.time.LocalDateTime;
import java.util.List;

public record UpdateEventAdminModel(
        String name,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Integer locationId,
        List<Integer> tags,
        boolean visibility,
        boolean archived

) {

    public static UpdateEventAdminModel of(UpdateEventAdminDto event) {
        return new UpdateEventAdminModel(
                event.name(),
                event.description(),
                event.startDate(),
                event.endDate(),
                event.locationId(),
                event.tags(),
                event.visibility(),
                event.archived()
        );
    }


}