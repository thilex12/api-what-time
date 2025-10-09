package fr.isencaen.api_what_time.service.Model;

import fr.isencaen.api_what_time.controller.Dto.EventFilterDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public record EventFilterModel(
        Optional<String> name,
        Optional<LocalDateTime> startDate,
        Optional<LocalDateTime> endDate,
        Optional<Boolean> visible,
        Optional<Integer> ownerId,
        Optional<LocalDateTime> beforeDate,
        Optional<LocalDateTime> afterDate,
        Optional<String> location,
        List<Integer> tags,
        Boolean isArchived
) {
    public static EventFilterModel of(EventFilterDto eventFilterDto) {
        return new EventFilterModel(
                eventFilterDto.name(),
                eventFilterDto.startDate(),
                eventFilterDto.endDate(),
                eventFilterDto.visible(),
                eventFilterDto.ownerId(),
                eventFilterDto.beforeDate(),
                eventFilterDto.afterDate(),
                eventFilterDto.location(),
                eventFilterDto.tags(),
                eventFilterDto.isArchived()
        );
    }

}
