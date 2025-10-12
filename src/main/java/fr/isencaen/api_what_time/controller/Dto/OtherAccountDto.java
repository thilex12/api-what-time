package fr.isencaen.api_what_time.controller.Dto;

import fr.isencaen.api_what_time.service.Model.AccountModel;

public record OtherAccountDto (
        int id,
        String name,
        String surname,
        String mail
){
    public static OtherAccountDto of(AccountModel account) {
        return new OtherAccountDto(
                account.id(),
                account.name(),
                account.surname(),
                account.mail()
        );
    }
}
