package fr.isencaen.api_what_time.controller.Dto;

public record UpdateAccountDto(
        String name,
        String surname,
        String mail,
        String mdp
) {
}
