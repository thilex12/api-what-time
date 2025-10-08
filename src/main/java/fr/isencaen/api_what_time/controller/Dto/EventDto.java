package fr.isencaen.api_what_time.controller.Dto;

public record EventDto (
        int id,
        String name,
        String description,
        String creationDate,
        String startDate,
        String endDate,
        String location,
        boolean visibility

){


}
