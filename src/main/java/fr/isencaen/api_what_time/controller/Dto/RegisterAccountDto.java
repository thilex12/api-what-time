package fr.isencaen.api_what_time.controller.Dto;

import fr.isencaen.api_what_time.service.Model.AccountModel;

public record RegisterAccountDto (
        int id, // Uniquement pour retour au client
        String name,
        String surname,
        String mail,
        String mdp
){
    public static RegisterAccountDto of(AccountModel account){
        return new RegisterAccountDto(account.id(), account.name(), account.surname(), account.mail(), account.mdp());
    }
}
