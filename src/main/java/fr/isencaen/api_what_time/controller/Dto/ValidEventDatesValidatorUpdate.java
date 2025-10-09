package fr.isencaen.api_what_time.controller.Dto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidEventDatesValidatorUpdate implements ConstraintValidator<ValidEventDatesUpdate, UpdateEventDto> {

    @Override
    public void initialize(ValidEventDatesUpdate constraintAnnotation) {
    }

    @Override
    public boolean isValid(UpdateEventDto dto, ConstraintValidatorContext context) {
        if (dto.startDate() == null || dto.endDate() == null) return true;
        return dto.startDate().isBefore(dto.endDate());
    }
}