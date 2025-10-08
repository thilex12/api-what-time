package fr.isencaen.api_what_time.controller.Dto;

import fr.isencaen.api_what_time.service.Model.AccountModel;

public record AccountDto (
        int id,
        String name,
        String surname,
        String mail
){
    public static AccountDto of(AccountModel account){
        return new AccountDto(
                account.id(), account.name(), account.surname(), account.mail()
        );
    }
}
