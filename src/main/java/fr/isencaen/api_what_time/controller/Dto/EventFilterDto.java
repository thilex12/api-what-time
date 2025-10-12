package fr.isencaen.api_what_time.controller.Dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public record EventFilterDto(
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

}
