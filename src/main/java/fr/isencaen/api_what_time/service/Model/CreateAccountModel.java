package fr.isencaen.api_what_time.service.Model;

import fr.isencaen.api_what_time.controller.Dto.RegisterAccountDto;

public record CreateAccountModel(
        String name,
        String surname,
        String mail,
        String pwd
) {
    public static CreateAccountModel of(RegisterAccountDto account){
        return new CreateAccountModel(account.name(), account.surname(), account.mail(), account.pwd());
    }
}
