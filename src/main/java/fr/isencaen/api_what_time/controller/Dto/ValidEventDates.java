package fr.isencaen.api_what_time.controller.Dto;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidEventDatesValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEventDates {
    String message() default "La date de début doit être avant la date de fin";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}