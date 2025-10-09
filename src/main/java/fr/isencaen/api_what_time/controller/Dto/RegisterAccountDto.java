package fr.isencaen.api_what_time.controller.Dto;

public record RegisterAccountDto (
        String name,
        String surname,
        String mail,
        String pwd
){
}
