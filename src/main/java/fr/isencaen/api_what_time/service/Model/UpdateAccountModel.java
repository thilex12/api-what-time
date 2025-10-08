package fr.isencaen.api_what_time.service.Model;

import fr.isencaen.api_what_time.controller.Dto.UpdateAccountDto;

public record UpdateAccountModel(
        String name,
        String surname,
        String mail,
        String pwd
) {
    public static UpdateAccountModel of(UpdateAccountDto account){
        return new UpdateAccountModel(account.name(), account.surname(), account.mail(), account.pwd());
    }
}