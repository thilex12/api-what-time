package fr.isencaen.api_what_time.controller.Dto;

import fr.isencaen.api_what_time.service.Model.AccountModel;

public record RegisterAccountDto (
        String name,
        String surname,
        String mail,
        String mdp
){
}
