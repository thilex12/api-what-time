package fr.isencaen.api_what_time.controller.Dto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidEventDatesValidator implements ConstraintValidator<ValidEventDates, CreateEventDto> {

    @Override
    public void initialize(ValidEventDates constraintAnnotation) {
    }

    @Override
    public boolean isValid(CreateEventDto dto, ConstraintValidatorContext context) {
        if (dto.startDate() == null || dto.endDate() == null) return true;
        return dto.startDate().isBefore(dto.endDate());
    }
}