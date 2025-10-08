package fr.isencaen.api_what_time.service.Model;

public record EventModel (
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