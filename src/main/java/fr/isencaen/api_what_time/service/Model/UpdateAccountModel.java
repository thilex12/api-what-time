package fr.isencaen.api_what_time.service.Model;

import fr.isencaen.api_what_time.controller.Dto.UpdateAccountDto;

import java.util.List;

public record UpdateAccountModel(
        String name,
        String surname,
        String mail,
        String pwd,
        List<Integer> tags
) {
    public static UpdateAccountModel of(UpdateAccountDto account){
        return new UpdateAccountModel(
                account.name(),
                account.surname(),
                account.mail(),
                account.pwd(),
                account.tags()
        );
    }
}