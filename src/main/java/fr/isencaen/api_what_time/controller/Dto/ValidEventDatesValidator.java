package fr.isencaen.api_what_time.controller.Dto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidEventDatesValidator implements ConstraintValidator<ValidEventDates, Object> {

    @Override
    public void initialize(ValidEventDates constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        return switch (object) {
            case CreateEventDto dto ->
                    dto.startDate() == null || dto.endDate() == null || dto.startDate().isBefore(dto.endDate());
            case UpdateEventDto dto ->
                    dto.startDate() == null || dto.endDate() == null || dto.startDate().isBefore(dto.endDate());
            default -> true;
        };
    }
}